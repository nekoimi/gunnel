package com.nekoimi.gunnel.server.gunnel;

import com.nekoimi.gunnel.server.context.GunnelContext;
import com.nekoimi.gunnel.server.contract.GunnelApplication;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/16 22:26
 */
@Slf4j
public abstract class AbstractGunnelApplication implements GunnelApplication {
    private final String name;
    private final GunnelContext context;
    public AbstractGunnelApplication(String name, GunnelContext context) {
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
    public void shutdown() {
        context.latch().countDown();
    }

    @Override
    public void run() {
        start();
    }
}
