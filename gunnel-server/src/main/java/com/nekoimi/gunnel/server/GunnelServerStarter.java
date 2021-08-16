package com.nekoimi.gunnel.server;

import com.nekoimi.gunnel.server.config.GunnelConfigApplication;
import com.nekoimi.gunnel.server.gunnel.GunnelContainer;
import com.nekoimi.gunnel.server.context.GunnelContext;
import com.nekoimi.gunnel.server.gunnel.GunnelTcpServer;
import com.nekoimi.gunnel.server.gunnel.GunnelUdpServer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/13 20:52
 */
@Slf4j
public class GunnelServerStarter {
    private static final GunnelContext context = new GunnelContext();
    private final EventLoopGroup masterLoop = new NioEventLoopGroup(1);
    private final EventLoopGroup workerLoop = new NioEventLoopGroup();
    private final GunnelContainer container = new GunnelContainer();

    public void run() {
        context.bindEventLoop(masterLoop, workerLoop);
        try {
            container.register(new GunnelConfigApplication("GunnelServerConfigApp", context));
            container.register(new GunnelUdpServer("GunnelUdpServerApp", context));
            container.register(new GunnelTcpServer("GunnelTcpServerApp", context));
            container.runAll();
            context.waitGroup();
        } finally {
            context.forceShutdown();
        }
    }

    public static void main(String[] args) {
        new GunnelServerStarter().run();
    }
}
