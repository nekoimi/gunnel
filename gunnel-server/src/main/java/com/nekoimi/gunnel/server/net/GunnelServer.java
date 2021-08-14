package com.nekoimi.gunnel.server.net;

import com.nekoimi.gunnel.server.initializer.GunnelServerInitializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * nekoimi  2021/8/14 17:33
 */
public class GunnelServer extends AbstractServer {
    @Override
    public ChannelInitializer<SocketChannel> initializer() {
        return new GunnelServerInitializer();
    }
}
