package com.nekoimi.gunnel.server;

import com.nekoimi.gunnel.server.config.GunnelConfigApplication;
import com.nekoimi.gunnel.server.context.GunnelContext;
import com.nekoimi.gunnel.server.gunnel.GunnelContainer;
import com.nekoimi.gunnel.server.gunnel.GunnelTcpServer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * nekoimi  2021/8/13 20:52
 */
@Slf4j
public class GunnelServer {
    private static final GunnelContext context = new GunnelContext();
    private final EventLoopGroup masterLoop = new NioEventLoopGroup(1, new DefaultThreadFactory("masterLoop"));
    private final EventLoopGroup workerLoop = new NioEventLoopGroup(new DefaultThreadFactory("workerLoop"));
    private final GunnelContainer container = new GunnelContainer();

    public void start() {
        try {
            CountDownLatch latch;
            context.bindEventLoop(masterLoop, workerLoop);
            container.register(new GunnelConfigApplication("GunnelServerConfigApp", context));
            container.register(new GunnelTcpServer("GunnelTcpServerApp", context));
            latch = container.runAll();
            context.bindLatch(latch);
            log.debug("Config: {}", context.properties());
            latch.await();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        } finally {
            context.forceShutdown();
        }
    }

    public static void main(String[] args) {
        new GunnelServer().start();
    }
}
