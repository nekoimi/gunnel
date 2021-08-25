package com.nekoimi.gunnel.server.gunnel;

import com.nekoimi.gunnel.server.context.GunnelContext;
import com.nekoimi.gunnel.server.contract.ProxyApplication;
import com.nekoimi.gunnel.server.event.ShutdownEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/16 22:26
 */
@Slf4j
public abstract class GunnelApplication implements ProxyApplication {
    protected final String name;
    protected final GunnelContext context;
    public GunnelApplication(String name, GunnelContext context) {
        this.name = name;
        this.context = context;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public GunnelContext context() {
        return context;
    }

    @Override
    public void restart() {
        // TODO
    }

    @Override
    public void shutdown(ShutdownEvent event) {
        // TODO
    }

    @Override
    public void run() {
        start();
    }
}
