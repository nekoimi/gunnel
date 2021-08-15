package com.nekoimi.gunnel.client.handler;

import com.nekoimi.gunnel.common.handler.GunnelMessageHandler;
import com.nekoimi.gunnel.common.protocol.message.*;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/14 20:59
 */
@Slf4j
public class GunnelClientHandler extends GunnelMessageHandler {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        sendAuth("10000", "123456");
    }

    @Override
    protected void gunnelReadAuth(ChannelHandlerContext ctx, Auth message) {
        log.debug("Auth success!");
    }

    @Override
    protected void gunnelReadRegister(ChannelHandlerContext ctx, Register message) {
        log.debug("Register success!");
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
    protected void gunnelReadError(ChannelHandlerContext ctx, GunnelError message) {
        log.debug("-- 收到来自服务器端的错误消息 --");
        ctx.close();
    }
}
