package com.nekoimi.gunnel.server.context;

import com.nekoimi.gunnel.server.config.ServerProperties;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/16 21:55
 */
@Slf4j
public class GunnelContext {
    private EventLoopGroup masterLoop;
    private EventLoopGroup workerLoop;
    private ChannelFuture future;
    private ServerProperties properties;

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

    public ChannelFuture future() {
        return future;
    }

    public ServerProperties properties() {
        return properties;
    }

    public void waitGroup() {
        try {
            future().sync();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        } finally {
            forceShutdown();
        }
    }

    public void forceShutdown() {
        if (!workerLoop.isShutdown()) {
            workerLoop.shutdownGracefully();
        }
        if (!masterLoop.isShutdown()) {
            masterLoop.shutdownGracefully();
        }
    }
}
