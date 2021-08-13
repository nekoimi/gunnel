package com.nekoimi.gunnel.server.net;

/**
 * nekoimi  2021/8/13 23:26
 */
public interface Server {
    /**
     * start server
     */
    void run();

    /**
     * restart server
     */
    void restart();

    /**
     * stop server
     */
    void shutdown();
}
