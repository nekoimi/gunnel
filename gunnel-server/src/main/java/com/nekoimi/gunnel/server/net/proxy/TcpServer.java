package com.nekoimi.gunnel.server.net.proxy;

import com.nekoimi.gunnel.server.initializer.TcpServerInitializer;
import com.nekoimi.gunnel.server.net.AbstractServer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/13 23:32
 */
@Slf4j
public class TcpServer extends AbstractServer {
    private final int port;
    public TcpServer(int port) {
        this.port = port;
    }

    @Override
    public ChannelInitializer<? extends Channel> initializer() {
        return new TcpServerInitializer();
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
