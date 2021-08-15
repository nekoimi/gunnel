package com.nekoimi.gunnel.server.handler;

import com.nekoimi.gunnel.common.handler.GunnelMessageHandler;
import com.nekoimi.gunnel.common.protocol.message.*;
import com.nekoimi.gunnel.server.auth.ChannelAuthService;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/14 16:50
 *
 * Gunnel 专用的消息处理器
 * 主要用来接受非代理消息，例如远程代理客户端注册、状态监控等
 * >> TODO 会有多个客户端连接过来，一个客户端就需要建立一个Server连接
 */
@Slf4j
public class GunnelServerHandler extends GunnelMessageHandler {
    @Override
    protected void gunnelReadAuth(ChannelHandlerContext ctx, Auth message) {
        log.debug("-- 验证认证信息 --");
        if (!ChannelAuthService.auth(message)) {
            sendError(-1);
        }

        else {
            sendAuth(null, null);
        }
    }

    @Override
    protected void gunnelReadRegister(ChannelHandlerContext ctx, Register message) {
        log.debug("Register: " + message.toJson());
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

    }
}
