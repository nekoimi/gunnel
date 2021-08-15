package com.nekoimi.gunnel.client.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * nekoimi  2021/8/14 20:49
 */
public abstract class AbstractClient {
    protected final NioEventLoopGroup workerGroup = new NioEventLoopGroup();
    protected final Bootstrap bootstrap = new Bootstrap();

    /**
     * 连接类型
     * Tcp: NioSocketChannel
     * Udp: NioDatagramChannel
     * @return
     */
    abstract public Class<? extends Channel> channel();

    /**
     * 连接自定义的初始化方法
     * @return
     */
    abstract public ChannelInitializer<? extends Channel> initializer();

    /**
     * 具体的连接实现
     */
    abstract public void connect();

    public void start() {
        // init
        this.bootstrap.group(workerGroup).channel(channel()).handler(initializer());
        // start connect
        connect();
    }
}
