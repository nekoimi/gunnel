package com.nekoimi.gunnel.server.context;

import com.nekoimi.gunnel.server.config.ServerProperties;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * nekoimi  2021/8/16 21:55
 */
@Slf4j
public class GunnelContext {
    private CountDownLatch latch;
    private EventLoopGroup masterLoop;
    private EventLoopGroup workerLoop;
    private ChannelFuture future;
    private ServerProperties properties;

    public void bindLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    public void bindEventLoop(EventLoopGroup masterLoop, EventLoopGroup workerLoop) {
        this.masterLoop = masterLoop;
        this.workerLoop = workerLoop;
    }

    public void bindFuture(ChannelFuture future) {
        this.future = future;
    }

    public void setProperties(ServerProperties properties) {
        this.properties = properties;
    }

    public CountDownLatch latch() {
        return latch;
    }

    public EventLoopGroup masterLoop() {
        return masterLoop;
    }

    public EventLoopGroup workerLoop() {
        return workerLoop;
    }

    public ChannelFuture future() {
        return future;
    }

    public ServerProperties properties() {
        return properties;
    }

    public void forceShutdown() {
        if (!masterLoop.isShutdown()) {
            masterLoop.shutdownGracefully();
        }
        if (!workerLoop.isShutdown()) {
            workerLoop.shutdownGracefully();
        }
    }
}
