package com.nekoimi.gunnel.server.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * nekoimi  2021/8/13 23:39
 */
public abstract class AbstractServer {
    private final static Logger logger = LoggerFactory.getLogger(AbstractServer.class);

    private final EventLoopGroup masterGroup = new NioEventLoopGroup(1);
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    private final ServerBootstrap bootstrap = new ServerBootstrap();
    private Channel channel;


    abstract public ChannelInitializer<SocketChannel> initializer();


    public void start() {
        bootstrap.group(masterGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .handler(new LoggingHandler())
                .childHandler(initializer());
        try {
            channel = bootstrap.bind(9933)
                    .addListener((ChannelFutureListener) channelFuture -> logger.debug("Listen to port: " + 9933))
                    .sync()
                    .channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        } finally {
            forceShutdown();
        }
    }

    public void restart() {
    }

    public void shutdown() {
        if (channel.isOpen()) {
            channel.close();
        }
        forceShutdown();
    }

    public void forceShutdown() {
        workerGroup.shutdownGracefully();
        masterGroup.shutdownGracefully();
    }
}
