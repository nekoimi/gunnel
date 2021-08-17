package com.nekoimi.gunnel.common.handler;

import com.nekoimi.gunnel.common.protocol.GunnelMessage;
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
}
