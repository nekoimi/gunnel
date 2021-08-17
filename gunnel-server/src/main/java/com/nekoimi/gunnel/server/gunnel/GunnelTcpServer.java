package com.nekoimi.gunnel.server.gunnel;

import com.nekoimi.gunnel.common.codec.GunnelMessageDecoder;
import com.nekoimi.gunnel.common.codec.GunnelMessageEncoder;
import com.nekoimi.gunnel.server.context.GunnelContext;
import com.nekoimi.gunnel.server.handler.GunnelServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * nekoimi  2021/8/16 23:47
 */
@Slf4j
public class GunnelTcpServer extends AbstractGunnelApplication {
    private final ServerBootstrap bootstrap = new ServerBootstrap();
    private Channel channel;

    public GunnelTcpServer(String name, GunnelContext context) {
        super(name, context);
        bootstrap.group(context().masterLoop(), context().workerLoop())
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .handler(new LoggingHandler())
                .childHandler(new GunnelTcpServerInitializer());
    }

    @Override
    public void start() {
        channel = bootstrap.bind(context().properties().getPort()).addListener(future -> {
            log.info("{} listen port {}......", name(), context().properties().getPort());
        }).channel();
        channel.closeFuture().addListener(future -> {
            log.info("{} channel close......", name());
        });
    }

    @Override
    public void restart() {
        context().masterLoop().execute(() -> {
            do {
                log.info("尝试重启 {}......", name());
                channel.close();
                try {
                    TimeUnit.SECONDS.sleep(5);
                    start();
                    break;
                } catch (InterruptedException e) {
                    log.error("{} 重启失败, {}", name(), e.getMessage());
                }
            } while (true);
        });
        super.restart();
    }

    @Override
    public void shutdown() {
        channel.close();
        super.shutdown();
    }

    private final static class GunnelTcpServerInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            /**
             * Netty的TCP分包处理器
             * >> TODO 粘包、半包初始配置，根据自定义的协议
             * @see com.nekoimi.gunnel.common.codec.GunnelMessageDecoder
             * >> TODO Length需要偏移两个字段（版本号 + 消息类型）
             * >> TODO Length 本身长度为4，不需要补长度，也不需要跳过某些字段
             * >> TODO 这样解码之后 ByteBuf 里面就包含全部的数据 => 版本号 + 消息类型 + 长度 + 数据
             */
            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 12, 4, 0, 0));
            // Idle 空闲时间处理机制
            pipeline.addLast(new IdleStateHandler(60, 30, 0));
            // 自定义协议编码解码器
            pipeline.addLast(new GunnelMessageDecoder());
            pipeline.addLast(new GunnelMessageEncoder());
            // Gunnel Server 逻辑实现处理
            pipeline.addLast(new GunnelServerHandler());
        }
    }
}
