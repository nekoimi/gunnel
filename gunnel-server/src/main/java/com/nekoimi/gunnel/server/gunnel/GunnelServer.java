package com.nekoimi.gunnel.server.gunnel;

import com.google.common.eventbus.Subscribe;
import com.nekoimi.gunnel.common.codec.GunnelMessageDecoder;
import com.nekoimi.gunnel.common.codec.GunnelMessageEncoder;
import com.nekoimi.gunnel.server.config.ConfigApplication;
import com.nekoimi.gunnel.server.context.GunnelContext;
import com.nekoimi.gunnel.server.event.ShutdownEvent;
import com.nekoimi.gunnel.server.handler.GunnelServerHandler;
import com.nekoimi.gunnel.server.handler.ServerIdleCheckHandler;
import com.nekoimi.gunnel.server.proxy.ProxyApplication;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * nekoimi  2021/8/16 23:47
 */
@Slf4j
public class GunnelServer extends GunnelApplication {
    private final ServerBootstrap bootstrap = new ServerBootstrap();
    private final GunnelApplication configApp;
    private final ConcurrentMap<String, ProxyApplication> container = new ConcurrentHashMap<>();
    private Channel channel;

    public GunnelServer(String name, GunnelContext context) {
        super(name, context);
        this.configApp = new ConfigApplication("default-config", context);
        bootstrap.group(context().masterLoop, context().workerLoop)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .handler(new LoggingHandler())
                .childHandler(new GunnelServerInitializer(context()));
    }

    public void start() {
        configApp.start();
        context().eventBus.register(this);
        channel = bootstrap.bind(context().properties().getPort()).addListener(future -> {
            container.forEach((s, pa) -> pa.start());
            if (future.isSuccess()) {
                log.info("{} start success! listening on port {}", name(), context().properties().getPort());
            } else {
                log.error("{} start on {} failed! {}", name(), context().properties().getPort(), future.cause().getMessage());
            }
        }).channel();
        ChannelFuture future = channel.closeFuture().addListener(cf -> {
            log.info("{} channel close...", name());
            shutdown(ShutdownEvent.of());
        });
        try {
            future.sync();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            shutdown(ShutdownEvent.of());
        }
    }

    public void restart() {
        context().workerLoop.execute(() -> {
            for (;;) {
                try {
                    log.info("try restarting the {}...", name());
                    shutdown(ShutdownEvent.of());
                    TimeUnit.SECONDS.sleep(5);
                    start();
                    log.info("restart {} success!", name());
                    break;
                } catch (InterruptedException var1) {
                    log.error("{} restart failed, {}", name(), var1.getMessage());
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException var2) {
                        log.error(var2.getMessage());
                    }
                }
            }
        });
    }

    @Subscribe
    public void shutdown(ShutdownEvent event) {
        log.info("{} shutdown...", name());
        if (channel != null && channel.isOpen()) {
            channel.close();
        }
    }

    /**
     * 添加其他服务
     *
     * @param proxy
     */
    public void extend(ProxyApplication proxy) {
        container.putIfAbsent(proxy.name(), proxy);
    }

    private final static class GunnelServerInitializer extends ChannelInitializer<SocketChannel> {
        private final GunnelContext context;

        public GunnelServerInitializer(GunnelContext context) {
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
