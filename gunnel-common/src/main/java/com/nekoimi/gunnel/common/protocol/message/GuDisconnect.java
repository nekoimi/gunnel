package com.nekoimi.gunnel.common.protocol.message;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * nekoimi  2021/8/15 12:22
 */
@Data
@AllArgsConstructor(staticName = "of")
public class GuDisconnect {
    private String channelId;
}
