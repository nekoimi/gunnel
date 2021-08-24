package com.nekoimi.gunnel.server.gunnel;

import com.nekoimi.gunnel.server.context.GunnelContext;
import com.nekoimi.gunnel.server.contract.GunnelProxyApplication;

/**
 * nekoimi  2021/8/17 10:27
 */
public abstract class AbstractProxyApplication extends AbstractGunnelApplication implements GunnelProxyApplication {
    public AbstractProxyApplication(String name, GunnelContext context) {
        super(name, context);
    }
}
