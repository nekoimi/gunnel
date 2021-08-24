package com.nekoimi.gunnel.server.net.proxy;

import com.nekoimi.gunnel.server.config.ProxyProperties;
import com.nekoimi.gunnel.server.context.GunnelContext;
import com.nekoimi.gunnel.server.gunnel.AbstractProxyApplication;
import com.nekoimi.gunnel.server.handler.GunnelServerHandler;
import com.nekoimi.gunnel.server.handler.ProxyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/13 23:32
 */
@Slf4j
public class TcpServer extends AbstractProxyApplication {
    private final int port;
    private final GunnelServerHandler parentHandler;
    public final ServerBootstrap bootstrap = new ServerBootstrap();

    public TcpServer(String name, GunnelContext context, int port, GunnelServerHandler parentHandler) {
        super(name, context);

        this.port = port;
        this.parentHandler = parentHandler;
    }

    @Override
    public void start() {
        bootstrap.group(context().masterLoop(), context().workerLoop())
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .handler(new LoggingHandler()).childHandler(initializer())
                .bind(port).channel().closeFuture().addListener(future -> log.debug("close..."));
    }

    @Override
    public void register(ProxyProperties properties) {

    }

    public ChannelInitializer<? extends Channel> initializer() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new ByteArrayDecoder());
                pipeline.addLast(new ByteArrayEncoder());
                pipeline.addLast(new ProxyServerHandler(parentHandler));
                // >> TODO append socket channel to proxy channel group
                parentHandler.channels().add(ch);
            }
        };
    }
}
