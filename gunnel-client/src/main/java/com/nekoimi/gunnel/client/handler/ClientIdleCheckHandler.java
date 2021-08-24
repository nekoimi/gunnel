package com.nekoimi.gunnel.client.handler;

import com.nekoimi.gunnel.common.enums.EMessage;
import com.nekoimi.gunnel.common.protocol.GunnelMessage;
import com.nekoimi.gunnel.common.protocol.message.GuKeepalive;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/24 15:59
 */
@Slf4j
public class ClientIdleCheckHandler extends IdleStateHandler {
    public ClientIdleCheckHandler() {
        super(0, 30, 0);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        if (IdleStateEvent.FIRST_WRITER_IDLE_STATE_EVENT == evt) {
            log.info("idle check reader, send keepalive message");
            ctx.writeAndFlush(GunnelMessage.of(EMessage.GU_KEEPALIVE, GuKeepalive.of()));
            return; // Ignore next handler.
        }

        super.channelIdle(ctx, evt);
    }
}
