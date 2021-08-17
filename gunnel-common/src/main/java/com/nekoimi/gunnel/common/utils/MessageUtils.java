package com.nekoimi.gunnel.common.utils;

import com.nekoimi.gunnel.common.config.ClientProperties;
import com.nekoimi.gunnel.common.config.HttpProxyProperties;
import com.nekoimi.gunnel.common.config.TcpProxyProperties;
import io.netty.channel.ChannelHandlerContext;

/**
 * nekoimi  2021/8/16 15:00
 */
public class MessageUtils {

    public static void sendAuth(ChannelHandlerContext ctx, ClientProperties.ID id) {
//        ctx.writeAndFlush(GunnelMessage.buildAuth(id.getIdentifier(), id.getKey()));
    }

    public static void sendAuthAsk(ChannelHandlerContext ctx) {
//        ctx.writeAndFlush(GunnelMessage.buildAuth(null, null));
    }

    public static void senError(ChannelHandlerContext ctx, int code) {
//        ctx.writeAndFlush(GunnelMessage.buildError(code));
    }

    public static void sendTcpRegister(ChannelHandlerContext ctx, TcpProxyProperties properties) {
//        Register register = Register.builder().EProtocol(EProtocol.TCP).tcpProperties(properties).build();
//        ctx.writeAndFlush(GunnelMessage.builder().type(EMessage.GU_REGISTER).message(register).build());
    }

    public static void sendHttpRegister(ChannelHandlerContext ctx, HttpProxyProperties properties) {
//        Register register = Register.builder().EProtocol(EProtocol.TCP).httpProperties(properties).build();
//        ctx.writeAndFlush(GunnelMessage.builder().type(EMessage.GU_REGISTER).message(register).build());
    }

    public static void sendConnected(ChannelHandlerContext ctx, String channelId) {
//        ctx.writeAndFlush(GunnelMessage.builder().type(EMessage.GU_CONNECTED).message(new Connected(channelId)).build());
    }

    public static void sendDisconnected(ChannelHandlerContext ctx, String channelId) {
//        ctx.writeAndFlush(GunnelMessage.builder().type(EMessage.GU_DISCONNECTED).message(new Disconnected(channelId)).build());
    }

    public static void sendData(ChannelHandlerContext ctx, String channelId, byte[] data) {
//        ctx.writeAndFlush(GunnelMessage.builder().type(EMessage.GU_DATA).message(new Data(channelId, data)).build());
    }
}
