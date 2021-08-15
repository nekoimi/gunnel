package com.nekoimi.gunnel.server.handler;

import com.nekoimi.gunnel.common.handler.GunnelMessageHandler;
import com.nekoimi.gunnel.common.protocol.message.*;
import com.nekoimi.gunnel.common.utils.MessageSender;
import com.nekoimi.gunnel.server.auth.ChannelAuthService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/14 16:50
 *
 * Gunnel 服务端消息处理器
 * 主要用来接受非代理消息，例如远程代理客户端注册、状态监控等
 * >> TODO 会有多个客户端连接过来，一个客户端就需要建立一个Server连接
 */
@Slf4j
public class GunnelServerHandler extends GunnelMessageHandler {
    // channel group for proxy channel
    private static final ChannelGroup proxyChannelGroup = new DefaultChannelGroup("ProxyChannelGroup", GlobalEventExecutor.INSTANCE);

    @Override
    protected void gunnelReadAuth(ChannelHandlerContext ctx, Auth message) {
        log.debug("-- 验证认证信息 --");
        if (!ChannelAuthService.auth(message)) {
            MessageSender.error(-1);
        }

        else {
            MessageSender.auth();
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
