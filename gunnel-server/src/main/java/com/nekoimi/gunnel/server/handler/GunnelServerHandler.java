package com.nekoimi.gunnel.server.handler;

import com.nekoimi.gunnel.common.enums.EMessage;
import com.nekoimi.gunnel.common.handler.GunnelMessageHandler;
import com.nekoimi.gunnel.common.protocol.GunnelMessage;
import com.nekoimi.gunnel.server.context.GunnelContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/14 16:50
 * <p>
 * Gunnel 服务端消息处理器
 * 主要用来接受非代理消息，例如远程代理客户端注册、状态监控等
 * >> TODO 会有多个客户端连接过来，一个客户端就需要建立一个Server连接
 */
@Slf4j
public class GunnelServerHandler extends GunnelMessageHandler {
    private static final String GROUP_NAME = "ChannelGroup-";
    private final ChannelGroup channels;
    private final ChannelId channelId;
    private final GunnelContext context;

    public GunnelServerHandler(GunnelContext context, ChannelId channelId) {
        log.debug("-- new GunnelServerHandler --, channelId: {}", channelId.asShortText());
        this.context = context;
        this.channelId = channelId;
        channels = new DefaultChannelGroup(GROUP_NAME + channelId.asShortText(), GlobalEventExecutor.INSTANCE);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.debug("-- [channelActive] channelId: {} --", ctx.channel().id().asShortText());
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GunnelMessage msg) throws Exception {
        log.debug("------------------------ GunnelMessageHandler BEGIN ------------------------");
        log.debug("message type: " + msg.getType());
        log.debug("message content: " + msg.toJsonMessage());
        EMessage type = msg.getType();
        Object message = msg.getMessage();
        log.debug("------------------------ GunnelMessageHandler BEGIN ------------------------");
    }
}
