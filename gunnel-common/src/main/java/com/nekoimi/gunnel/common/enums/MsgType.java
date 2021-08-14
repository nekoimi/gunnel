package com.nekoimi.gunnel.common.enums;

import com.nekoimi.gunnel.common.errors.GunnelRuntimeException;

/**
 * nekoimi  2021/8/14 14:22
 */
public enum MsgType {
    GU_REGISTER(1),
    GU_CONNECTED(2),
    GU_DISCONNECTED(3),
    GU_DATA(4),
    GU_KEEPALIVE(5),
    GU_ERROR(9)
    ;

    private final int code;

    MsgType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static MsgType valueOf(int code) {
        for (MsgType msgType : MsgType.values()) {
            if (msgType.getCode() == code) {
                return msgType;
            }
        }

        throw new GunnelRuntimeException();
    }
}
