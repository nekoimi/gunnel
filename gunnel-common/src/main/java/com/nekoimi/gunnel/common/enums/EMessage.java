package com.nekoimi.gunnel.common.enums;

import com.nekoimi.gunnel.common.errors.GunnelRuntimeException;
import com.nekoimi.gunnel.common.protocol.message.*;

/**
 * nekoimi  2021/8/14 14:22
 */
public enum EMessage {
    // gunnel server <=> client
    GU_LOGIN(0, GuLogin.class),
    GU_KEEPALIVE(1, GuKeepalive.class),
    GU_REGISTER(2, GuRegister.class),

    // gunnel proxy server <=> client
    GU_CONNECTED(3, GuConnected.class),
    GU_DATA(4, GuData.class),
    GU_DISCONNECTED(5, GuDisconnected.class),

    // gunnel error
    GU_ERROR(9, GuError.class)
    ;

    private final int code;
    private final Class<?> type;

    EMessage(int code, Class<?> type) {
        this.code = code;
        this.type = type;
    }

    public int getCode() {
        return code;
    }

    public Class<?> getType() {
        return type;
    }

    public static EMessage valueOf(int code) {
        for (EMessage EMessage : EMessage.values()) {
            if (EMessage.getCode() == code) {
                return EMessage;
            }
        }

        throw new GunnelRuntimeException();
    }
}
