package com.nekoimi.gunnel.server.contract;

import com.nekoimi.gunnel.server.context.GunnelContext;
import com.nekoimi.gunnel.server.event.ShutdownEvent;

/**
 * nekoimi  2021/8/16 21:04
 */
public interface ProxyApplication extends Runnable {
    /**
     * 获取应用名称
     *
     * @return
     */
    String name();

    /**
     * 获取应用上下文
     *
     * @return
     */
    GunnelContext context();

    /**
     * 启动
     */
    void start();

    /**
     * 重启
     */
    void restart();

    /**
     * 停止
     */
    void shutdown(ShutdownEvent event);
}
