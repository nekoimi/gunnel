package com.nekoimi.gunnel.server.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * nekoimi  2021/8/13 23:39
 */
public abstract class AbstractServer {
    private final static Logger logger = LoggerFactory.getLogger(AbstractServer.class);
    public final EventLoopGroup masterGroup = new NioEventLoopGroup(1);
    public final EventLoopGroup workerGroup = new NioEventLoopGroup();
    public final ServerBootstrap bootstrap = new ServerBootstrap();


    /**
     * 连接自定义的初始化方法
     * @return
     */
    abstract public ChannelInitializer<? extends Channel> initializer();

    /**
     * 具体监听实现
     */
    abstract protected void bind();

    public void start() {
        bootstrap.group(masterGroup, workerGroup).channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .handler(new LoggingHandler()).childHandler(initializer());
        // start bind
        bind();
    }

    public void forceShutdown() {
        workerGroup.shutdownGracefully();
        masterGroup.shutdownGracefully();
    }
}
