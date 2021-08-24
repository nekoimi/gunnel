package com.nekoimi.gunnel.server.handler;

import com.nekoimi.gunnel.common.enums.EMessage;
import com.nekoimi.gunnel.common.protocol.GunnelMessage;
import com.nekoimi.gunnel.common.protocol.message.GuData;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/15 18:36
 */
@Slf4j
public class ProxyServerHandler extends SimpleChannelInboundHandler<Object> {
    private final GunnelServerHandler parentHandler;
    public ProxyServerHandler(GunnelServerHandler parentHandler) {
        this.parentHandler = parentHandler;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.debug("--- ProxyServerHandler --- \n {}", msg);

        parentHandler.handlerContext().writeAndFlush(GunnelMessage.of(EMessage.GU_DATA, GuData.of(parentHandler.channelId().asShortText(), (byte[]) msg)));
    }
//
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        log.debug("master channel id: " + masterHandler.context().channel().id().asShortText());
//
//        log.debug("-- connected --");
//        log.debug(ctx.channel().id().asShortText());
//
//        new Thread(() -> {
//            while (true) {
//                try {
//                    TimeUnit.SECONDS.sleep(10);
//                    MessageUtils.sendAuthAsk(masterHandler.context());
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//        MessageUtils.sendConnected(masterHandler.context(), ctx.channel().id().asShortText());
//    }
//
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        log.debug("master channel id: " + masterHandler.context().channel().id().asShortText());
//
//        log.debug("-- disconnected --");
//        log.debug(ctx.channel().id().asShortText());
//        MessageUtils.sendDisconnected(masterHandler.context(), ctx.channel().id().asShortText());
//    }
//
//    @Override
//    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
//        log.debug("master channel id: " + masterHandler.context().channel().id().asShortText());
//
//        log.debug("-- forward --");
//        byte[] data = (byte[]) msg;
//        String s = new String(data);
//        log.debug("msg: " + s);
//        MessageUtils.sendData(masterHandler.context(), ctx.channel().id().asShortText(), data);
//    }
}
