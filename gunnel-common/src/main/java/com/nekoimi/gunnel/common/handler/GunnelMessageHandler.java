package com.nekoimi.gunnel.common.handler;

import com.nekoimi.gunnel.common.contract.Message;
import com.nekoimi.gunnel.common.enums.MsgType;
import com.nekoimi.gunnel.common.protocol.GunnelMessage;
import com.nekoimi.gunnel.common.protocol.message.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/14 21:19
 */
@Slf4j
public abstract class GunnelMessageHandler extends SimpleChannelInboundHandler<GunnelMessage> {

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) throws Exception {
        log.error(e.getMessage());
        if (log.isDebugEnabled()) {
            e.printStackTrace();
        }
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GunnelMessage msg) throws Exception {
        log.debug("------------------------ GunnelMessageHandler BEGIN ------------------------");
        log.debug("message type: " + msg.getType());
        log.debug("message content: " + msg.getMessage().toJson());
        MsgType type = msg.getType();
        Message message = msg.getMessage();

        if (MsgType.GU_AUTH == type) {
            gunnelReadAuth(ctx, (Auth) message);
        }

        if (MsgType.GU_REGISTER == type) {
            gunnelReadRegister(ctx, (Register) message);
        }

        if (MsgType.GU_CONNECTED == type) {
            gunnelReadConnected(ctx, (Connected) message);
        }

        if (MsgType.GU_DISCONNECTED == type) {
            gunnelReadDisconnected(ctx, (Disconnected) message);
        }

        if (MsgType.GU_DATA == type) {
            gunnelReadData(ctx, (Data) message);
        }

        if (MsgType.GU_KEEPALIVE == type) {
            gunnelReadKeepalive(ctx, (Keepalive) message);
        }

        if (MsgType.GU_ERROR == type) {
            gunnelReadError(ctx, (GunnelError) message);
        }

        log.debug("------------------------ GunnelMessageHandler BEGIN ------------------------");
    }

    /**
     * 认证消息
     *
     * @param ctx
     */
    abstract protected void gunnelReadAuth(ChannelHandlerContext ctx, Auth message);

    /**
     * 注册消息
     *
     * @param ctx
     */
    abstract protected void gunnelReadRegister(ChannelHandlerContext ctx, Register message);

    /**
     * 连接消息
     *
     * @param ctx
     */
    abstract protected void gunnelReadConnected(ChannelHandlerContext ctx, Connected message);

    /**
     * 断开连接消息
     *
     * @param ctx
     */
    abstract protected void gunnelReadDisconnected(ChannelHandlerContext ctx, Disconnected message);

    /**
     * 转发数据
     *
     * @param ctx
     */
    abstract protected void gunnelReadData(ChannelHandlerContext ctx, Data message);

    /**
     * 心跳
     *
     * @param ctx
     */
    protected void gunnelReadKeepalive(ChannelHandlerContext ctx, Keepalive message) {
        // TODO
    }

    /**
     * 错误通知
     *
     * @param ctx
     */
    abstract protected void gunnelReadError(ChannelHandlerContext ctx, GunnelError message);

}
