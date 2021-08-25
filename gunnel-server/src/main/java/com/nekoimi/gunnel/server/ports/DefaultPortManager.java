package com.nekoimi.gunnel.server.ports;

import com.nekoimi.gunnel.server.contract.PortManager;

import java.util.concurrent.CopyOnWriteArraySet;

/**
 * nekoimi  2021/8/25 16:11
 * <p>
 * 端口使用记录
 */
public class DefaultPortManager implements PortManager {
    private final CopyOnWriteArraySet<Port> usedPorts = new CopyOnWriteArraySet<>();

    @Override
    public void used(Port port) {
        usedPorts.add(port);
    }

    @Override
    public void unUsed(Port port) {
        usedPorts.remove(port);
    }

    @Override
    public boolean idUsed(Port port) {
        return usedPorts.contains(port);
    }
}
