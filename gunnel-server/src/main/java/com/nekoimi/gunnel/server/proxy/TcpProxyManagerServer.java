package com.nekoimi.gunnel.server.proxy;

import com.google.common.eventbus.Subscribe;
import com.nekoimi.gunnel.server.context.GunnelContext;
import com.nekoimi.gunnel.server.event.ProxyUnRegisterClientEvent;
import com.nekoimi.gunnel.server.event.ProxyUnRegisterEvent;
import com.nekoimi.gunnel.server.event.ShutdownEvent;
import com.nekoimi.gunnel.server.event.ProxyRegisterEvent;
import com.nekoimi.gunnel.server.handler.ProxyServerHandler;
import com.nekoimi.gunnel.server.ports.Port;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * nekoimi  2021/8/24 16:24
 */
@Slf4j
public class TcpProxyManagerServer extends ProxyApplication {
    private final ServerBootstrap bootstrap = new ServerBootstrap();
    private final ConcurrentMap<Port, Channel> portOfChannels = new ConcurrentHashMap<>();

    public TcpProxyManagerServer(String name, GunnelContext context) {
        super(name, context);
    }

    @Override
    public void start() {
        context().eventBus.register(this);
        bootstrap.group(context().masterLoop, context().workerLoop)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .handler(new LoggingHandler());
    }

    @Override
    @Subscribe
    public void shutdown(ShutdownEvent event) {
        portOfChannels.forEach((port, channel) -> {
            if (channel != null && channel.isOpen()) {
                channel.close();
            }
            log.info("bind on {} channel close...", port);
        });
        portOfChannels.clear();
        context().eventBus.unregister(this);
    }

    @Override
    @Subscribe
    public void register(ProxyRegisterEvent event) {
        // >> TODO initializer
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new ByteArrayDecoder());
                pipeline.addLast(new ByteArrayEncoder());
                pipeline.addLast(new ProxyServerHandler(context.eventBus));
                // >> TODO append socket channel to proxy channel group
//                parentHandler.channels().add(ch);
            }
        });
        // >> TODO bind and listen
        ChannelFuture bindFuture = bootstrap.bind(event.getPort().getValue()).addListener(bf -> {
            if (bf.isSuccess()) {
                log.info("{} bind to {} success!", name(), event.getPort());
            } else {
                log.error("{} bind on {} failed! {}", name(), event.getPort(), bf.cause().getMessage());
            }
        });
        bindFuture.channel().closeFuture().addListener(cf -> {
            log.info("bind on {} channel close...", event.getPort());
        });
    }

    @Override
    @Subscribe
    public void unregister(ProxyUnRegisterEvent event) {

    }

    @Override
    @Subscribe
    public void unregister(ProxyUnRegisterClientEvent event) {

    }
}
