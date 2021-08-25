package com.nekoimi.gunnel.server.event;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * nekoimi  2021/8/25 15:43
 *
 * TCP 代理注册事件
 */
@Data
@AllArgsConstructor(staticName = "event")
public class TcpProxyRegisterEvent {
    private int port;
}
