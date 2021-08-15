package com.nekoimi.gunnel.common.enums;

import com.nekoimi.gunnel.common.contract.Message;
import com.nekoimi.gunnel.common.errors.GunnelRuntimeException;
import com.nekoimi.gunnel.common.protocol.message.*;
import com.nekoimi.gunnel.common.protocol.message.Error;

/**
 * nekoimi  2021/8/14 14:22
 */
public enum MsgType {
    GU_REGISTER(1, Register.class),
    GU_CONNECTED(2, Connected.class),
    GU_DISCONNECTED(3, Disconnected.class),
    GU_DATA(4, Data.class),
    GU_KEEPALIVE(5, Keepalive.class),
    GU_ERROR(9, Error.class)
    ;

    private final int code;
    private final Class<? extends Message> messageType;

    MsgType(int code, Class<? extends Message> messageType) {
        this.code = code;
        this.messageType = messageType;
    }

    public int getCode() {
        return code;
    }

    public Class<? extends Message> getMessageType() {
        return messageType;
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
