package com.nekoimi.gunnel.client.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * nekoimi  2021/8/14 20:49
 */
public abstract class AbstractClient {
    private final static Logger logger = LoggerFactory.getLogger(AbstractClient.class);
    private final NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    private final Bootstrap bootstrap = new Bootstrap();

    abstract public String host();
    abstract public int port();
    abstract public ChannelInitializer<SocketChannel> initializer();

    public void start() {
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(initializer())
                .option(ChannelOption.SO_KEEPALIVE, true);
        try {
            Channel channel = bootstrap.connect(host(), port()).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        } finally {
            workerGroup.shutdownGracefully();
        }
    }
}
