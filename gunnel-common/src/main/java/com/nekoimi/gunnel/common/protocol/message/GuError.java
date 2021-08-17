package com.nekoimi.gunnel.common.protocol.message;

import lombok.Builder;
import lombok.Data;

/**
 * nekoimi  2021/8/15 12:24
 */
@Data
@Builder
public class GuError {
    private int code;
}
