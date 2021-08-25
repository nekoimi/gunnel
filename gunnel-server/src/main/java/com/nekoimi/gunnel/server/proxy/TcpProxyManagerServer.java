package com.nekoimi.gunnel.server.proxy;

import com.google.common.eventbus.Subscribe;
import com.nekoimi.gunnel.server.context.GunnelContext;
import com.nekoimi.gunnel.server.event.TcpProxyRegisterEvent;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CopyOnWriteArraySet;

/**
 * nekoimi  2021/8/24 16:24
 */
@Slf4j
public class TcpProxyManagerServer extends ProxyApplication {
    private final ServerBootstrap bootstrap = new ServerBootstrap();
    private final CopyOnWriteArraySet<Integer> bindPorts = new CopyOnWriteArraySet<>();

    public TcpProxyManagerServer(String name, GunnelContext context) {
        super(name, context);
    }

    @Override
    public void start() {
        context().eventBus.register(this);
        bootstrap.group(context().masterLoop, context().workerLoop)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .handler(new LoggingHandler())
                .childHandler(new TCPProxyServerInitializer(context()));
    }

    @Override
    public void shutdown() {
        context().eventBus.unregister(this);
        super.shutdown();
    }

    @Subscribe
    public void register(TcpProxyRegisterEvent event) {
        bootstrap.bind(event.getPort()).addListener(bf -> {
            if (bf.isSuccess()) {
                log.info("{} bind to {} success!", name(), event.getPort());
            } else {
                log.error("{} bind on {} failed! {}", name(), event.getPort(), bf.cause().getMessage());
            }
        }).channel().closeFuture().addListener(cf -> {
            log.debug("bind on {} channel close...", event.getPort());
        });
    }


    private final static class TCPProxyServerInitializer extends ChannelInitializer<SocketChannel> {
        private final GunnelContext context;

        private TCPProxyServerInitializer(GunnelContext context) {
            this.context = context;
        }

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new ByteArrayDecoder());
            pipeline.addLast(new ByteArrayEncoder());
//                pipeline.addLast(new ProxyServerHandler(parentHandler));
            // >> TODO append socket channel to proxy channel group
//                parentHandler.channels().add(ch);
        }
    }
}
