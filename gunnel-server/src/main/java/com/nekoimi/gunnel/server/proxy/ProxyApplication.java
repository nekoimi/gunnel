package com.nekoimi.gunnel.server.proxy;

import com.nekoimi.gunnel.server.context.GunnelContext;
import com.nekoimi.gunnel.server.event.ProxyUnRegisterEvent;
import com.nekoimi.gunnel.server.event.ProxyRegisterEvent;
import com.nekoimi.gunnel.server.event.ProxyUnRegisterClientEvent;
import com.nekoimi.gunnel.server.gunnel.GunnelApplication;

/**
 * nekoimi  2021/8/17 10:27
 */
public abstract class ProxyApplication extends GunnelApplication {
    public ProxyApplication(String name, GunnelContext context) {
        super(name, context);
    }

    /**
     * 客户端注册新的代理，clientId和proxy需要绑定起来
     * @param event
     */
    abstract public void register(ProxyRegisterEvent event);

    /**
     * 客户端取消proxy注册
     * @param event
     */
    abstract public void unregister(ProxyUnRegisterEvent event);

    /**
     * 清除指定client所注册的全部proxy
     * @param event
     */
    abstract public void unregister(ProxyUnRegisterClientEvent event);
}
