package com.nekoimi.gunnel.common.event;

/**
 * nekoimi  2021/8/25 14:33
 */
public class GunnelEventBus extends com.google.common.eventbus.EventBus implements EventBus {
    public GunnelEventBus(String identifier) {
        super(identifier);
    }

    @Override
    public void publish(Object event) {
        super.post(event);
    }
}
