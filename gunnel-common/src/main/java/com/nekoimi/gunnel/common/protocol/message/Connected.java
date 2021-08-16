package com.nekoimi.gunnel.common.protocol.message;

import com.nekoimi.gunnel.common.contract.Message;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * nekoimi  2021/8/15 12:22
 */
@Data
@AllArgsConstructor
public class Connected implements Message {
    private String channelId;
}
