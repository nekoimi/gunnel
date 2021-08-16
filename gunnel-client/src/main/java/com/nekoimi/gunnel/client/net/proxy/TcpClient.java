package com.nekoimi.gunnel.client.net.proxy;

import com.nekoimi.gunnel.client.handler.GunnelClientHandler;
import com.nekoimi.gunnel.client.handler.ProxyClientHandler;
import com.nekoimi.gunnel.client.net.AbstractClient;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/16 16:03
 */
@Slf4j
public class TcpClient extends AbstractClient {
    private final GunnelClientHandler masterHandler;
    private final String remoteChannelId;
    private final String host;
    private final int port;

    public TcpClient(String remoteChannelId, GunnelClientHandler masterHandler, String host, int port) {
        this.remoteChannelId = remoteChannelId;
        this.masterHandler = masterHandler;
        this.host = host;
        this.port = port;
    }

    @Override
    public Class<? extends Channel> channel() {
        return NioSocketChannel.class;
    }

    @Override
    public ChannelInitializer<? extends Channel> initializer() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new ByteArrayDecoder());
                pipeline.addLast(new ByteArrayEncoder());
                pipeline.addLast(new ProxyClientHandler(masterHandler, remoteChannelId));
            }
        };
    }

    @Override
    public void connect() {
        try {
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            Channel channel = bootstrap.connect(host, port).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}
