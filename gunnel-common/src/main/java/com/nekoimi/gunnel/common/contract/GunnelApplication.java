package com.nekoimi.gunnel.common.contract;

/**
 * nekoimi  2021/8/16 20:59
 */
public interface GunnelApplication extends Runnable {
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
    void shutdown();
}
