package com.nekoimi.gunnel.common.protocol.message;

import lombok.Builder;
import lombok.Data;

/**
 * nekoimi  2021/8/15 12:58
 */
@Data
@Builder
public class GuAuth {
    private String identifier;
    private String idKey;
}
