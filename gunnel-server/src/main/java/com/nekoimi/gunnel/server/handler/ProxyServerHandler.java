package com.nekoimi.gunnel.server.handler;

import com.nekoimi.gunnel.common.event.EventBus;
import com.nekoimi.gunnel.server.event.ConnectEvent;
import com.nekoimi.gunnel.server.event.DataEvent;
import com.nekoimi.gunnel.server.event.DisconnectEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/15 18:36
 */
@Slf4j
public class ProxyServerHandler extends SimpleChannelInboundHandler<Object> {
    private final EventBus eventBus;
    public ProxyServerHandler(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    /**
     * 通知客户端和代理服务建立连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("--- ProxyServerHandler --- channelActive: {}", ctx.channel().id());
        eventBus.publish(ConnectEvent.of());
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
        eventBus.publish(DataEvent.of());
    }

    /**
     * 通知客户端断开连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.debug("--- ProxyServerHandler --- channelInactive: {}", ctx.channel().id());
        eventBus.publish(DisconnectEvent.of());
    }
}
