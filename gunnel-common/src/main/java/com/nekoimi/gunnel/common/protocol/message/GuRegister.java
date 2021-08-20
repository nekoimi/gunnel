package com.nekoimi.gunnel.common.protocol.message;

import com.nekoimi.gunnel.common.config.HttpProxyProperties;
import com.nekoimi.gunnel.common.config.TcpProxyProperties;
import com.nekoimi.gunnel.common.enums.EProtocol;
import lombok.Builder;
import lombok.Data;

/**
 * nekoimi  2021/8/14 18:13
 */
@Data
@Builder(builderMethodName = "of")
public class GuRegister {
    private EProtocol EProtocol;
    private TcpProxyProperties tcpProperties;
    private HttpProxyProperties httpProperties;
}
