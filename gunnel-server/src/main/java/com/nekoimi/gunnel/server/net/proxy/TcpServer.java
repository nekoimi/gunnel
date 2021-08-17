package com.nekoimi.gunnel.server.net.proxy;

import com.nekoimi.gunnel.server.handler.GunnelServerHandler;
import com.nekoimi.gunnel.server.net.AbstractServer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/13 23:32
 */
@Slf4j
public class TcpServer extends AbstractServer {
    private final int port;
    private final GunnelServerHandler masterHandler;

    public TcpServer(int port, GunnelServerHandler masterHandler) {
        this.port = port;
        this.masterHandler = masterHandler;
    }

    @Override
    public ChannelInitializer<? extends Channel> initializer() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new ByteArrayDecoder());
                pipeline.addLast(new ByteArrayEncoder());
//                pipeline.addLast(new ProxyServerHandler(masterHandler));
                // >> TODO append socket channel to proxy channel group
            }
        };
    }

    @Override
    protected void bind() {
        try {
            bootstrap.bind(port).sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        } finally {
            forceShutdown();
        }
    }
}
