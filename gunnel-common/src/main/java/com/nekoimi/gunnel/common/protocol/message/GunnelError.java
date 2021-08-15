package com.nekoimi.gunnel.common.protocol.message;

import com.nekoimi.gunnel.common.contract.Message;
import lombok.Builder;
import lombok.Data;

/**
 * nekoimi  2021/8/15 12:24
 */
@Data
@Builder
public class GunnelError implements Message {
    private int code;
}
