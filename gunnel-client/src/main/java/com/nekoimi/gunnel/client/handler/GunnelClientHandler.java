package com.nekoimi.gunnel.client.handler;

import com.nekoimi.gunnel.common.config.TcpProxyProperties;
import com.nekoimi.gunnel.common.enums.EMessage;
import com.nekoimi.gunnel.common.enums.EProtocol;
import com.nekoimi.gunnel.common.handler.GunnelMessageHandler;
import com.nekoimi.gunnel.common.protocol.GunnelMessage;
import com.nekoimi.gunnel.common.protocol.message.GuRegister;
import com.nekoimi.gunnel.common.protocol.request.GuLoginReq;
import com.nekoimi.gunnel.common.protocol.response.GuLoginResp;
import com.nekoimi.gunnel.common.utils.IdUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
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
        String hostname = ctx.channel().localAddress().toString();
        String os = System.getProperty("os.name");
        String arch = System.getProperty("os.arch");
        String user = System.getProperty("user.name");
        long clientId = IdUtils.randLong();
        GuLoginReq loginReq = GuLoginReq.of("", hostname, os, arch, user, clientId, null);
        ctx.writeAndFlush(GunnelMessage.of(EMessage.GU_LOGIN_REQ, loginReq));
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            if (evt == IdleStateEvent.WRITER_IDLE_STATE_EVENT) {
                log.debug("-- Idle state event -- {}, need send keepalive message to server!", evt);
                ByteBuf ping = Unpooled.copiedBuffer("ping", StandardCharsets.UTF_8);
                ctx.writeAndFlush(ping);
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
        if (GuLoginResp.class.equals(type.getType())) {
            resultMessage = handleLoginResp((GuLoginResp) message);
        }

        // other, the message type invalid...
        else {
            log.warn("The message type ( {} ) invalid...", type);
        }

        if (resultMessage != null) {
            ctx.writeAndFlush(resultMessage);
        }
    }

    protected GunnelMessage handleLoginResp(GuLoginResp message) {
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
