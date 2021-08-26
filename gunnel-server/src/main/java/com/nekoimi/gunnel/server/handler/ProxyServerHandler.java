package com.nekoimi.gunnel.server.handler;

import com.nekoimi.gunnel.common.enums.EMessage;
import com.nekoimi.gunnel.common.protocol.GunnelMessage;
import com.nekoimi.gunnel.common.protocol.message.GuConnect;
import com.nekoimi.gunnel.common.protocol.message.GuData;
import com.nekoimi.gunnel.common.protocol.message.GuDisconnect;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/15 18:36
 */
@Slf4j
public class ProxyServerHandler extends SimpleChannelInboundHandler<Object> {
    private final GunnelServerHandler serverHandler;
    public ProxyServerHandler(GunnelServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

    /**
     * 通知客户端和代理服务建立连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("--- ProxyServerHandler --- channelActive: {}", ctx.channel().id());
        serverHandler.clientContext().writeAndFlush(GunnelMessage.of(EMessage.GU_CONNECT, GuConnect.of("")));
    }

    /**
     * 转发流量到客户端
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.debug("--- ProxyServerHandler --- channelRead0:\n {}", msg);
        byte[] data = (byte[]) msg;
        serverHandler.clientContext().writeAndFlush(GunnelMessage.of(EMessage.GU_DATA, GuData.of("", data)));
    }

    /**
     * 通知客户端断开连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug("--- ProxyServerHandler --- channelInactive: {}", ctx.channel().id());
        serverHandler.clientContext().writeAndFlush(GunnelMessage.of(EMessage.GU_DISCONNECT, GuDisconnect.of("")));
    }
}
