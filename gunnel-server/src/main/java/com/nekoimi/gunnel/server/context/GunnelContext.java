package com.nekoimi.gunnel.server.context;

import com.nekoimi.gunnel.common.enums.EProtocol;
import com.nekoimi.gunnel.common.event.EventBus;
import com.nekoimi.gunnel.server.config.ServerProperties;
import com.nekoimi.gunnel.server.contract.PortManager;
import com.nekoimi.gunnel.server.ports.DefaultPortManager;
import com.nekoimi.gunnel.server.ports.Port;
import io.netty.channel.EventLoopGroup;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/16 21:55
 */
@Slf4j
public class GunnelContext {
    public final EventBus eventBus;
    public final EventLoopGroup masterLoop;
    public final EventLoopGroup workerLoop;
    public final PortManager portManager;
    private ServerProperties properties;

    public GunnelContext(EventBus eventBus, EventLoopGroup masterLoop, EventLoopGroup workerLoop) {
        this.eventBus = eventBus;
        this.masterLoop = masterLoop;
        this.workerLoop = workerLoop;
        this.portManager = new DefaultPortManager();
    }

    public void setProperties(ServerProperties serverProperties) {
        properties = serverProperties;
        portManager.used(Port.of(EProtocol.TCP, properties().getPort()));
        portManager.used(Port.of(EProtocol.UDP, properties().getPort()));
    }

    public ServerProperties properties() {
        return properties;
    }
}
