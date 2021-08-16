package com.nekoimi.gunnel.common.utils;

import com.nekoimi.gunnel.common.errors.GunnelRuntimeException;

/**
 * nekoimi  2021/8/16 21:30
 */
public class GunnelAssert {
    public static void notNull(Object o) {
        if (o == null) {
            throw new GunnelRuntimeException();
        }
    }
}
