package com.nekoimi.gunnel.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * nekoimi  2021/8/17 13:52
 * <p>
 * 服务端空闲检测
 */
@Slf4j
public class ServerIdleCheckHandler extends IdleStateHandler {
    public ServerIdleCheckHandler(long readerIdleTime) {
        super(readerIdleTime, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf data = (ByteBuf) msg;
        if (data.isReadable() && data.refCnt() >= 1) {
            int length = data.readableBytes();
            if (length > 0) {
                byte[] bytes = new byte[length];
                data.getBytes(0, bytes);
                String message = new String(bytes);
                log.debug("-- Idle state read -- ping: {}", message);
            }
        }
        ByteBuf pong = Unpooled.copiedBuffer("pong", StandardCharsets.UTF_8);
        ctx.writeAndFlush(pong);

        super.channelRead(ctx, msg);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        if (IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT == evt) {
            SocketAddress address = ctx.channel().remoteAddress();
            log.info("idle check reader, the current channel ( {} ) will be closed", address);
            ctx.close();
            return; // Ignore next handler.
        }
        super.channelIdle(ctx, evt);
    }
}
