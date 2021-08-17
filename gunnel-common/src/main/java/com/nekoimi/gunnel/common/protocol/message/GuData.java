package com.nekoimi.gunnel.common.protocol.message;

import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * nekoimi  2021/8/15 10:58
 */
@lombok.Data
@Builder
@AllArgsConstructor
public class GuData {
    private String channelId;
    private byte[] data;
}
