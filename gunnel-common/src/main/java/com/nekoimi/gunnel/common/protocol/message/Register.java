package com.nekoimi.gunnel.common.protocol.message;

import com.nekoimi.gunnel.common.contract.Message;
import lombok.Builder;
import lombok.Data;

/**
 * nekoimi  2021/8/14 18:13
 */
@Data
@Builder
public class Register implements Message {
    private int port;
    private String password;
}
