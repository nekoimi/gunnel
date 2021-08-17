package com.nekoimi.gunnel.client.handler;

import com.nekoimi.gunnel.common.config.GunnelConfigParser;
import com.nekoimi.gunnel.common.handler.GunnelMessageHandler;
import com.nekoimi.gunnel.common.protocol.GunnelMessage;
import com.nekoimi.gunnel.common.utils.MessageUtils;
import io.netty.channel.ChannelHandlerContext;
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
        MessageUtils.sendAuth(ctx, GunnelConfigParser.getClient().getId());
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

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GunnelMessage msg) throws Exception {

    }
}
