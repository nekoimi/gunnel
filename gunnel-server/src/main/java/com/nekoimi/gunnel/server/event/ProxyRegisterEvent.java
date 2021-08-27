package com.nekoimi.gunnel.server.event;

import com.nekoimi.gunnel.server.ports.Port;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * nekoimi  2021/8/25 15:43
 *
 * TCP 代理注册事件
 */
@Data
@AllArgsConstructor(staticName = "of")
public class ProxyRegisterEvent {
    private Port port;
}
