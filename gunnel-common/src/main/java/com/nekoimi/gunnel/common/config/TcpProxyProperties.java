package com.nekoimi.gunnel.common.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * nekoimi  2021/8/15 18:24
 */
@Getter
@Setter
@ToString
public class TcpProxyProperties {
    private String localIp;
    private int localPort;
    private int remotePort;
}
