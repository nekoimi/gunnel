package com.nekoimi.gunnel.server.net;

import com.nekoimi.gunnel.common.config.GunnelConfigParser;
import com.nekoimi.gunnel.common.context.GunnelContext;
import com.nekoimi.gunnel.server.initializer.GunnelServerInitializer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * nekoimi  2021/8/14 17:33
 *
 * Gunnel 服务端
 */
@Slf4j
public class GunnelServer extends AbstractServer {
    private final GunnelContext context;
    private final int port;

    public GunnelServer(int port) {
        this.port = port;
        this.context = new GunnelContext(new DefaultChannelGroup("ProxyChannelGroup", GlobalEventExecutor.INSTANCE));
    }

    @Override
    public ChannelInitializer<SocketChannel> initializer() {
        return new GunnelServerInitializer(context);
    }

    @Override
    protected void bind() {
        try {
            Channel channel = bootstrap.bind(port)
                    .addListener(future -> log.debug("GunnelServer running on " + port)).sync().channel();
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        } finally {
            forceShutdown();
        }
    }

    /**
     * Run
     * @param args
     */
    public static void run(String...args) {
        int port = GunnelConfigParser.getServer().getPort();
        new GunnelServer(port).start();
    }
}
