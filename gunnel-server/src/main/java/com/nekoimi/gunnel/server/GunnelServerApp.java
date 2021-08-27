package com.nekoimi.gunnel.server;

import com.nekoimi.gunnel.common.event.EventBus;
import com.nekoimi.gunnel.common.event.GunnelEventBus;
import com.nekoimi.gunnel.server.context.GunnelContext;
import com.nekoimi.gunnel.server.gunnel.GunnelServer;
import com.nekoimi.gunnel.server.proxy.TcpProxyManagerServer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/13 20:52
 */
@Slf4j
public class GunnelServerApp {

    public static void main(String[] args) {
        final EventBus eventBus = new GunnelEventBus("eventBus");
        final EventLoopGroup masterLoop = new NioEventLoopGroup(1, new DefaultThreadFactory("masterLoop"));
        final EventLoopGroup workerLoop = new NioEventLoopGroup(new DefaultThreadFactory("workerLoop"));
        final GunnelContext context = new GunnelContext(eventBus, masterLoop, workerLoop);
        try {
            GunnelServer gunnels = new GunnelServer("master-server", context);
            gunnels.extend(new TcpProxyManagerServer("proxy-tcp-server", context));
//            gunnels.extend("proxy-udp-server", null);
//            gunnels.extend("proxy-http-server", null);
//            gunnels.extend("proxy-https-server", null);
//
//            workerLoop.execute(() -> {
//                try {
//                    TimeUnit.SECONDS.sleep(5);
//                    log.debug("push event...");
//                    // >> TODO push event.
//                    eventBus.publish(TcpProxyRegisterEvent.event(Port.of(EProtocol.TCP, 10000)));
//                    eventBus.publish(TcpProxyRegisterEvent.event(Port.of(EProtocol.TCP, 10001)));
//                    eventBus.publish(TcpProxyRegisterEvent.event(Port.of(EProtocol.TCP, 10002)));
//                    eventBus.publish(TcpProxyRegisterEvent.event(Port.of(EProtocol.TCP, 10003)));
//                    eventBus.publish(TcpProxyRegisterEvent.event(Port.of(EProtocol.TCP, 10004)));
//
//
//                    TimeUnit.SECONDS.sleep(5);
//                    log.debug("push shutdown event...");
//                    eventBus.publish(ShutdownEvent.event());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            });
//
            gunnels.start();
        } catch (Exception e) {
            log.error(e.getMessage());
            if (log.isDebugEnabled()) {
                e.printStackTrace();
            }
        } finally {
            if (!masterLoop.isShutdown()) {
                masterLoop.shutdownGracefully();
            }
            if (!workerLoop.isShutdown()) {
                workerLoop.shutdownGracefully();
            }
        }
    }
}
