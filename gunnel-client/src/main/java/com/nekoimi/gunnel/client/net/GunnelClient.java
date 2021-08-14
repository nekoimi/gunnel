package com.nekoimi.gunnel.client.net;

import com.nekoimi.gunnel.client.initializer.GunnelClientInitializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * nekoimi  2021/8/14 20:49
 */
public class GunnelClient extends AbstractClient {
    @Override
    public String host() {
        return "192.168.3.3";
    }

    @Override
    public int port() {
        return 9933;
    }

    @Override
    public ChannelInitializer<SocketChannel> initializer() {
        return new GunnelClientInitializer();
    }
}
