package com.nekoimi.gunnel.common.utils;

import com.nekoimi.gunnel.common.config.ClientProperties;
import com.nekoimi.gunnel.common.protocol.GunnelMessage;
import io.netty.channel.ChannelHandlerContext;

/**
 * nekoimi  2021/8/16 15:00
 */
public class MessageUtils {

    public static void sendAuth(ChannelHandlerContext ctx, ClientProperties.ID id) {
        ctx.writeAndFlush(GunnelMessage.buildAuth(id.getIdentifier(), id.getKey()));
    }

    public static void sendAuthAsk(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(GunnelMessage.buildAuth(null, null));
    }

    public static void senError(ChannelHandlerContext ctx, int code) {
        ctx.writeAndFlush(GunnelMessage.buildError(code));
    }
}
