package com.nekoimi.gunnel.common.config;

import com.nekoimi.gunnel.common.enums.Protocol;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * nekoimi  2021/8/15 18:24
 */
@Getter
@Setter
@ToString
public class ProxyProperties {
    private Protocol type;
    private String localIp;
    private int localPort;
    private int remotePort;
}
