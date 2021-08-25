package com.nekoimi.gunnel.server.proxy;

import com.nekoimi.gunnel.server.context.GunnelContext;
import com.nekoimi.gunnel.server.event.TcpProxyRegisterEvent;
import com.nekoimi.gunnel.server.gunnel.GunnelApplication;

/**
 * nekoimi  2021/8/17 10:27
 */
public abstract class ProxyApplication extends GunnelApplication {
    public ProxyApplication(String name, GunnelContext context) {
        super(name, context);
    }

    /**
     * register
     * @param event
     */
    abstract public void register(TcpProxyRegisterEvent event);

    /**
     * unregister
     */
    abstract public void unregister();
}
