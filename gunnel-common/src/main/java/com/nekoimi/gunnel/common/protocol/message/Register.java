package com.nekoimi.gunnel.common.protocol.message;

import com.nekoimi.gunnel.common.config.HttpProxyProperties;
import com.nekoimi.gunnel.common.config.TcpProxyProperties;
import com.nekoimi.gunnel.common.contract.Message;
import com.nekoimi.gunnel.common.enums.Protocol;
import lombok.Builder;
import lombok.Data;

/**
 * nekoimi  2021/8/14 18:13
 */
@Data
@Builder
public class Register implements Message {
    private Protocol protocol;
    private TcpProxyProperties tcpProperties;
    private HttpProxyProperties httpProperties;
}
