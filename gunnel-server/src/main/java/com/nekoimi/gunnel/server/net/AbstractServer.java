package com.nekoimi.gunnel.server.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * nekoimi  2021/8/13 23:39
 */
public abstract class AbstractServer implements Server {
    private final static Logger logger = LoggerFactory.getLogger(AbstractServer.class);

    /**
     * master event loop
     */
    private final EventLoopGroup masterGroup = new NioEventLoopGroup(1);

    /**
     * worker event loop
     */
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();

    /**
     *
     */
    private Channel channel;

    /**
     * children channel initializer
     * @return
     */
    abstract public ChannelInitializer<SocketChannel> initializer();

    /**
     * used server channel type
     * @return
     */
    abstract public Class<? extends ServerChannel> channel();

    @Override
    public void run() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(masterGroup, workerGroup)
                .channel(channel())
                .childHandler(initializer())
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        try {
            channel = bootstrap.bind(9933).sync().channel();
            channel.closeFuture().addListener((ChannelFutureListener) channelFuture -> {
                workerGroup.shutdownGracefully();
                masterGroup.shutdownGracefully();
            });
        } catch (InterruptedException e) {
            try {
                logger.error(e.getMessage());
            } finally {
                workerGroup.shutdownGracefully();
                masterGroup.shutdownGracefully();
            }
        }
    }

    @Override
    public void restart() {

    }

    @Override
    public void shutdown() {
        channel.close();
    }
}
