package com.nekoimi.gunnel.server.gunnel;

import com.nekoimi.gunnel.common.codec.GunnelMessageDecoder;
import com.nekoimi.gunnel.common.codec.GunnelMessageEncoder;
import com.nekoimi.gunnel.server.context.GunnelContext;
import com.nekoimi.gunnel.server.handler.GunnelServerHandler;
import com.nekoimi.gunnel.server.handler.ServerIdleCheckHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
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
                .childHandler(new GunnelTcpServerInitializer(context()));
    }

    @Override
    public void start() {
        channel = bootstrap.bind(context().properties().getPort())
                .addListener(future -> {
                    if (future.isSuccess()) {
                        log.info("{} start success! listening on port {}", name(), context().properties().getPort());
                    } else {
                        log.error("{} start on {} failed! {}", name(), context().properties().getPort(), future.cause().getMessage());
                    }
                })
                .channel();
        channel.closeFuture().addListener(future -> {
            log.debug("{} channel close...", name());
            shutdown();
        });
    }

    @Override
    public void restart() {
        context().workerLoop().execute(() -> {
            do {
                log.info("try restarting the {}...", name());
                channel.close();
                try {
                    TimeUnit.SECONDS.sleep(5);
                    start();
                    log.info("restart {} success!", name());
                    break;
                } catch (InterruptedException e) {
                    log.error("{} restart failed, {}", name(), e.getMessage());
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException var1) {
                        log.error(var1.getMessage());
                    }
                }
            } while (true);
        });
        super.restart();
    }

    @Override
    public void shutdown() {
        log.info("{} shutdown...", name());
        if (channel != null && channel.isOpen()) {
            channel.close();
        }
        super.shutdown();
    }

    private final static class GunnelTcpServerInitializer extends ChannelInitializer<SocketChannel> {
        private final GunnelContext context;
        public GunnelTcpServerInitializer(GunnelContext context) {
            this.context = context;
        }

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            // Idle 空闲时间处理机制
            pipeline.addLast(new ServerIdleCheckHandler(context.properties().getIdleCheck().getReaderTime()));
            /**
             * Netty的TCP分包处理器
             * >> TODO 粘包、半包初始配置，根据自定义的协议
             * @see com.nekoimi.gunnel.common.codec.GunnelMessageDecoder
             * >> TODO Length需要偏移两个字段（版本号 + 消息类型）
             * >> TODO Length 本身长度为4，不需要补长度，也不需要跳过某些字段
             * >> TODO 这样解码之后 ByteBuf 里面就包含全部的数据 => 版本号 + 消息类型 + 长度 + 数据
             */
            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 12, 4, 0, 0));
            // 自定义协议编码解码器
            pipeline.addLast(new GunnelMessageDecoder());
            pipeline.addLast(new GunnelMessageEncoder());
            // Gunnel Server 逻辑实现处理
            pipeline.addLast(new GunnelServerHandler(context, ch.id()));
        }
    }
}
