package com.nekoimi.gunnel.common.utils;

import com.nekoimi.gunnel.common.config.ClientProperties;
import com.nekoimi.gunnel.common.enums.MsgType;
import com.nekoimi.gunnel.common.protocol.GunnelMessage;
import com.nekoimi.gunnel.common.protocol.message.Auth;
import com.nekoimi.gunnel.common.protocol.message.GunnelError;
import io.netty.channel.ChannelHandlerContext;

/**
 * nekoimi  2021/8/15 18:06
 */
public class MessageSender {
    private static ChannelHandlerContext context;

    public static void setContext(ChannelHandlerContext context) {
        MessageSender.context = context;
    }

    public static void auth() {
        context.writeAndFlush(GunnelMessage.builder().type(MsgType.GU_AUTH).message(
                Auth.builder().identifier(null).idKey(null).build())
                .build());
    }

    /**
     * 发送认证消息
     *
     * @param id
     */
    public static void auth(ClientProperties.ID id) {
        context.writeAndFlush(GunnelMessage.builder().type(MsgType.GU_AUTH).message(
                Auth.builder().identifier(id.getIdentifier()).idKey(id.getKey()).build())
                .build());
    }

    /**
     * 发送错误消息
     *
     * @param code
     */
    public static void error(int code) {
        context.writeAndFlush(GunnelMessage.builder().type(MsgType.GU_ERROR).message(
                GunnelError.builder().code(code).build())
                .build());
    }
}
