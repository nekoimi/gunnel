package com.nekoimi.gunnel.server.proxy;

import com.nekoimi.gunnel.server.context.GunnelContext;
import com.nekoimi.gunnel.server.gunnel.GunnelApplication;

/**
 * nekoimi  2021/8/17 10:27
 */
public abstract class ProxyApplication extends GunnelApplication {
    public ProxyApplication(String name, GunnelContext context) {
        super(name, context);
    }
}
