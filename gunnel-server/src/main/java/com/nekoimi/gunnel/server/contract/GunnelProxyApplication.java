package com.nekoimi.gunnel.server.contract;

import com.nekoimi.gunnel.server.config.ProxyProperties;

/**
 * nekoimi  2021/8/16 21:04
 */
public interface GunnelProxyApplication extends GunnelApplication {
    /**
     * 注册新的代理
     */
    void register(ProxyProperties properties);
}
