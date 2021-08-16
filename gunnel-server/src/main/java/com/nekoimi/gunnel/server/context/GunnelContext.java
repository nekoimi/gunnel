package com.nekoimi.gunnel.server.context;

import com.nekoimi.gunnel.server.config.ServerProperties;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;

/**
 * nekoimi  2021/8/16 21:55
 */
public class GunnelContext {
    private EventLoopGroup masterLoop;
    private EventLoopGroup workerLoop;
    private ChannelFuture tcpFuture;
    private ServerProperties properties;

    public void bindEventLoop(EventLoopGroup masterLoop, EventLoopGroup workerLoop) {
        this.masterLoop = masterLoop;
        this.workerLoop = workerLoop;
    }

    public void bindTcpFuture(ChannelFuture future) {
        this.tcpFuture = future;
    }

    public void setProperties(ServerProperties properties) {
        this.properties = properties;
    }

    public ChannelFuture tcpFuture() {
        return tcpFuture;
    }

    public ServerProperties properties() {
        return properties;
    }
}
