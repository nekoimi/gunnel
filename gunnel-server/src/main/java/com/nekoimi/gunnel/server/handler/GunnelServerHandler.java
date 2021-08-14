package com.nekoimi.gunnel.server.handler;

import com.nekoimi.gunnel.common.handler.GunnelMessageHandler;
import com.nekoimi.gunnel.common.protocol.GunnelMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

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
    protected void channelRead0(ChannelHandlerContext ctx, GunnelMessage msg) throws Exception {
        log.debug("------------------------ GunnelServerHandler BEGIN ------------------------");

        log.debug("msg: " + msg);

        ByteBuf buffer = ctx.alloc().buffer();
        buffer.writeBytes("hello world".getBytes(StandardCharsets.UTF_8));
        ctx.writeAndFlush(buffer);
        log.debug("------------------------ GunnelServerHandler BEGIN ------------------------");
    }
}
