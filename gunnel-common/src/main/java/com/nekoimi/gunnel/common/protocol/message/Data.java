package com.nekoimi.gunnel.common.protocol.message;

import com.nekoimi.gunnel.common.contract.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * nekoimi  2021/8/15 10:58
 */
@lombok.Data
@Builder
@AllArgsConstructor
public class Data implements Message {
    private String channelId;
    private byte[] data;
}
