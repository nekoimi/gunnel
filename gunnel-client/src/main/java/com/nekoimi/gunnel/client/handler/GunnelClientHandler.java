package com.nekoimi.gunnel.client.handler;

import com.nekoimi.gunnel.common.handler.GunnelMessageHandler;
import com.nekoimi.gunnel.common.protocol.GunnelMessage;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/14 20:59
 */
@Slf4j
public class GunnelClientHandler extends GunnelMessageHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GunnelMessage msg) throws Exception {
        log.debug("------------------------ GunnelClientHandler BEGIN ------------------------");
        log.debug(msg.toString());
        log.debug("------------------------ GunnelClientHandler BEGIN ------------------------");
    }
}
