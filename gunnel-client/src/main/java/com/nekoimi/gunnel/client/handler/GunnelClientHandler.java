package com.nekoimi.gunnel.client.handler;

import com.nekoimi.gunnel.common.config.TcpProxyProperties;
import com.nekoimi.gunnel.common.enums.EMessage;
import com.nekoimi.gunnel.common.enums.EProtocol;
import com.nekoimi.gunnel.common.handler.GunnelMessageHandler;
import com.nekoimi.gunnel.common.protocol.GunnelMessage;
import com.nekoimi.gunnel.common.protocol.message.GuKeepalive;
import com.nekoimi.gunnel.common.protocol.request.GuLoginReq;
import com.nekoimi.gunnel.common.protocol.message.GuRegister;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * nekoimi  2021/8/14 20:59
 */
@Slf4j
public class GunnelClientHandler extends GunnelMessageHandler {
    private final static ConcurrentMap<String, ProxyClientHandler> handlers = new ConcurrentHashMap<>();
    private ChannelHandlerContext context;

    public ChannelHandlerContext context() {
        return context;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.context = ctx;
        // 发送认证消息
        ctx.writeAndFlush(GunnelMessage.of(EMessage.GU_LOGIN_REQ, GuLoginReq.of()));
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            if (evt == IdleStateEvent.FIRST_WRITER_IDLE_STATE_EVENT) {
                log.debug("-- Idle state event -- {} send keepalive message to server!", evt);
                ctx.writeAndFlush(GunnelMessage.of(EMessage.GU_KEEPALIVE, GuKeepalive.of()));
                return;
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GunnelMessage msg) throws Exception {
        log.debug("--- {} ---", msg);
        EMessage type = msg.getType();
        Object message = msg.getMessage();
        GunnelMessage resultMessage = null;
        // >> TODO 服务器端返回验证成功， 需要提交代理注册信息
        if (GuLoginReq.class.equals(type.getType())) {
            resultMessage = handleLogin((GuLoginReq) message);
        }

        // other, the message type invalid...
        else {
            log.warn("The message type ( {} ) invalid...", type);
        }

        if (resultMessage != null) {
            ctx.writeAndFlush(resultMessage);
        }
    }

    protected GunnelMessage handleLogin(GuLoginReq message) {
        log.debug("--- ".concat(message.toString()).concat(" ---"));
        return GunnelMessage.of(EMessage.GU_REGISTER,
                GuRegister.of().EProtocol(EProtocol.TCP).tcpProperties(
                        TcpProxyProperties.of("192.168.1.202", 9000, 10000))
        );
    }

    //    @Override
//    protected void gunnelReadAuth(ChannelHandlerContext ctx, Auth message) {
//        log.debug("Auth success!");
//        // TODO 发送代理注册消息，将本地需要暴露的服务信息传送给服务器
//        ClientProperties.Proxy proxy = GunnelConfigParser.getClient().getProxy();
//        // tcp proxy properties
//        List<TcpProxyProperties> tcpList = proxy.getTcp();
//        for (TcpProxyProperties properties : tcpList) {
//            log.debug("Send register: " + properties);
//            MessageUtils.sendTcpRegister(ctx, properties);
//        }
//        // http proxy properties
//        List<HttpProxyProperties> httpList = proxy.getHttp();
//        for (HttpProxyProperties properties : httpList) {
//            log.debug("Send register: " + properties);
//            MessageUtils.sendHttpRegister(ctx, properties);
//        }
//    }
//
//    @Override
//    protected void gunnelReadRegister(ChannelHandlerContext ctx, Register message) {
//        log.debug("Register success!");
//    }
//
//    @Override
//    protected void gunnelReadConnected(ChannelHandlerContext ctx, Connected message) {
//        log.debug("-- try connected --");
//        GunnelClientHandler gunnelClientHandler = this;
//        String channelId = message.getChannelId();
//        new Thread(() -> {
//            log.debug("try connect......");
//            new TcpClient(channelId, gunnelClientHandler, "192.168.3.3", 10000).start();
//        }).start();
//    }
//
//    @Override
//    protected void gunnelReadDisconnected(ChannelHandlerContext ctx, Disconnected message) {
//        log.debug("-- try disconnected --");
//    }
//
//    @Override
//    protected void gunnelReadData(ChannelHandlerContext ctx, Data message) {
//        log.debug("-- try read data --");
//    }
//
//    @Override
//    protected void gunnelReadError(ChannelHandlerContext ctx, GunnelError message) {
//        log.debug("-- 收到来自服务器端的错误消息 --");
//        ctx.close();
//    }
}
