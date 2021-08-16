package com.nekoimi.gunnel.server;

import com.nekoimi.gunnel.server.config.GunnelConfigApplication;
import com.nekoimi.gunnel.server.gunnel.AppContainer;
import com.nekoimi.gunnel.server.context.GunnelContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/13 20:52
 */
@Slf4j
public class GunnelServer {
    private static final GunnelContext context = new GunnelContext();
    private final EventLoopGroup masterLoop = new NioEventLoopGroup(1);
    private final EventLoopGroup workerLoop = new NioEventLoopGroup();
    private final AppContainer container = new AppContainer();

    // Run all proxy
    private void runAllProxy() {
        context.bindEventLoop(masterLoop, workerLoop);
        container.register(new GunnelConfigApplication("gunnel-server-config", context));
        container.runAll();
    }

    // Run
    public void run() {
        new GunnelServer();
    }

    public static void main(String[] args) {
    }
}
