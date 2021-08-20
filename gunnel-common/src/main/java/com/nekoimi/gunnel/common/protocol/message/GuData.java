package com.nekoimi.gunnel.common.protocol.message;

import lombok.AllArgsConstructor;

/**
 * nekoimi  2021/8/15 10:58
 */
@lombok.Data
@AllArgsConstructor(staticName = "of")
public class GuData {
    private String channelId;
    private byte[] data;
}
