package com.nekoimi.gunnel.server.contract;

import com.nekoimi.gunnel.server.ports.Port;

/**
 * nekoimi  2021/8/25 16:12
 */
public interface PortManager {
    /**
     * @param port
     */
    void used(Port port);

    /**
     * @param port
     */
    void unUsed(Port port);

    /**
     * @param port
     * @return
     */
    boolean idUsed(Port port);
}
