package com.nekoimi.gunnel.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/16 16:01
 */
@Slf4j
public class ProxyClientHandler extends SimpleChannelInboundHandler<Object> {
    private final GunnelClientHandler masterHandler;
    private final String remoteChannelId;
    public ProxyClientHandler (GunnelClientHandler masterHandler, String remoteChannelId) {
        this.masterHandler = masterHandler;
        this.remoteChannelId = remoteChannelId;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.debug("msg: " + new String((byte[]) msg));
    }
}
