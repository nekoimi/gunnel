package com.nekoimi.gunnel.common.protocol;

import com.nekoimi.gunnel.common.config.ClientProperties;
import com.nekoimi.gunnel.common.enums.MsgType;
import com.nekoimi.gunnel.common.protocol.message.Auth;
import com.nekoimi.gunnel.common.protocol.message.GunnelError;
import io.netty.channel.ChannelHandlerContext;

/**
 * nekoimi  2021/8/15 21:09
 */
public class MessageSender {
    private ChannelHandlerContext ctx;

    public MessageSender(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public void auth() {
        ctx.writeAndFlush(GunnelMessage.builder().type(MsgType.GU_AUTH).message(
                Auth.builder().identifier(null).idKey(null).build())
                .build());
    }

    /**
     * 发送认证消息
     *
     * @param id
     */
    public void auth(ClientProperties.ID id) {
        ctx.writeAndFlush(GunnelMessage.builder().type(MsgType.GU_AUTH).message(
                Auth.builder().identifier(id.getIdentifier()).idKey(id.getKey()).build())
                .build());
    }

    /**
     * 发送错误消息
     *
     * @param code
     */
    public void error(int code) {
        ctx.writeAndFlush(GunnelMessage.builder().type(MsgType.GU_ERROR).message(
                GunnelError.builder().code(code).build())
                .build());
    }
}
