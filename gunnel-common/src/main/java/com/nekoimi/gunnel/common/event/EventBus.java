package com.nekoimi.gunnel.common.event;

/**
 * nekoimi  2021/8/25 14:32
 */
public interface EventBus {
    /**
     * 注册监听
     * @param object
     */
    void register(Object object);

    /**
     * 取消注册监听
     * @param object
     */
    void unregister(Object object);

    /**
     * 发布事件
     * @param event
     */
    void publish(Object event);
}
