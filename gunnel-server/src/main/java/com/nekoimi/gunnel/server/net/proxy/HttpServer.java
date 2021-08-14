package com.nekoimi.gunnel.server.net.proxy;

import com.nekoimi.gunnel.server.net.AbstractServer;
import com.nekoimi.gunnel.server.net.initializer.HttpServerInitializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * nekoimi  2021/8/13 23:27
 */
public class HttpServer extends AbstractServer {
    @Override
    public ChannelInitializer<SocketChannel> initializer() {
        return new HttpServerInitializer();
    }

}
