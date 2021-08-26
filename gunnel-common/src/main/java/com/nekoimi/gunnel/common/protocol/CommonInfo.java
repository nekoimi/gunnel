package com.nekoimi.gunnel.common.protocol;

import com.nekoimi.gunnel.common.constants.SystemConstants;
import lombok.Getter;
import lombok.Setter;

/**
 * nekoimi  2021/8/26 9:36
 */
@Getter
@Setter
public class CommonInfo {
    protected String version = SystemConstants.GUNNEL_VERSION;
    protected int code = 0;
    protected String message = "ok";
    protected long timestamp = System.currentTimeMillis();
}
