package com.nekoimi.gunnel.client.net;

import com.nekoimi.gunnel.client.initializer.GunnelClientInitializer;
import com.nekoimi.gunnel.common.config.ClientProperties;
import com.nekoimi.gunnel.common.config.GunnelConfigParser;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * nekoimi  2021/8/14 20:49
 *
 * Gunnel 客户端
 * 与服务器端建立一条 TCP 连接，转发数据
 */
@Slf4j
public class GunnelClient extends AbstractClient {
    private final String host;
    private final int port;

    public GunnelClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Class<? extends Channel> channel() {
        return NioSocketChannel.class;
    }

    @Override
    public ChannelInitializer<SocketChannel> initializer() {
        return new GunnelClientInitializer();
    }

    @Override
    public void connect() {
        try {
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            Channel channel = bootstrap.connect(host, port).sync().channel();
            channel.closeFuture().addListener(future -> closeReconnectListener()).sync();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    public void closeReconnectListener() {
        // 断线重连
        new Thread(new GunnelClientRetryReconnect()).start();
    }

    public final class GunnelClientRetryReconnect implements Runnable {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
            while (true) {
                try {
                    log.info("Try to reconnect to the server......");
                    connect();
                    break;
                } catch (Exception var1) {
                    log.error(var1.getMessage());
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException var2) {
                        log.error(var2.getMessage());
                    }
                }
            }
        }
    }

    /**
     * Run
     * @param args
     */
    public static void run(String...args) {
        ClientProperties.Server server = GunnelConfigParser.getClient().getServer();
        new GunnelClient(server.getAddress(), server.getPort()).start();
    }
}
