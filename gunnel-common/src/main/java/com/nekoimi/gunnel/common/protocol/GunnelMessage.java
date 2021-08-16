package com.nekoimi.gunnel.common.protocol;

import com.nekoimi.gunnel.common.contract.Message;
import com.nekoimi.gunnel.common.enums.MsgType;
import com.nekoimi.gunnel.common.protocol.message.Auth;
import com.nekoimi.gunnel.common.protocol.message.GunnelError;
import lombok.Builder;
import lombok.Data;

/**
 * nekoimi  2021/8/14 18:24
 */
@Data
@Builder
public class GunnelMessage {
    private MsgType type;
    private Message message;

    public static GunnelMessage buildAuth(String identifier, String idKey) {
        Auth auth = Auth.builder().identifier(identifier).idKey(idKey).build();
        return GunnelMessage.builder().type(MsgType.GU_AUTH).message(auth).build();
    }

    public static GunnelMessage buildError(int code) {
        GunnelError error = GunnelError.builder().code(code).build();
        return GunnelMessage.builder().type(MsgType.GU_ERROR).message(error).build();
    }
}
