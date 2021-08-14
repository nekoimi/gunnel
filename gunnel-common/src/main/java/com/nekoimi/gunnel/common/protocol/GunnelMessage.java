package com.nekoimi.gunnel.common.protocol;

import com.nekoimi.gunnel.common.enums.MsgType;
import lombok.Builder;
import lombok.Data;

/**
 * nekoimi  2021/8/14 18:24
 */
@Data
@Builder
public class GunnelMessage {
    private String channelId;
    private MsgType type;
    private Object message;
    private byte[] data;
}
