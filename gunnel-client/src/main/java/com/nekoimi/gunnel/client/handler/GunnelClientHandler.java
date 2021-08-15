package com.nekoimi.gunnel.client.handler;

import com.nekoimi.gunnel.common.enums.MsgType;
import com.nekoimi.gunnel.common.handler.GunnelMessageHandler;
import com.nekoimi.gunnel.common.protocol.GunnelMessage;
import com.nekoimi.gunnel.common.protocol.message.Connected;
import com.nekoimi.gunnel.common.protocol.message.Data;
import com.nekoimi.gunnel.common.protocol.message.Disconnected;
import com.nekoimi.gunnel.common.protocol.message.Register;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/14 20:59
 */
@Slf4j
public class GunnelClientHandler extends GunnelMessageHandler {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        GunnelMessage.builder()
                .type(MsgType.GU_REGISTER)
                .message(Register.builder().port(10000).password("123456").build())
                .build().send(ctx);

        super.channelActive(ctx);
    }

    @Override
    protected void gunnelReadRegister(ChannelHandlerContext ctx, Register message) {

    }

    @Override
    protected void gunnelReadConnected(ChannelHandlerContext ctx, Connected message) {

    }

    @Override
    protected void gunnelReadDisconnected(ChannelHandlerContext ctx, Disconnected message) {

    }

    @Override
    protected void gunnelReadData(ChannelHandlerContext ctx, Data message) {

    }

    @Override
    protected void gunnelReadError(ChannelHandlerContext ctx, Error message) {

    }
}
