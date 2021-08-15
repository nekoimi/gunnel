package com.nekoimi.gunnel.client.net;

import com.nekoimi.gunnel.client.initializer.GunnelClientInitializer;
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
    public String host() {
        return "192.168.3.3";
    }
    public int port() {
        return 9933;
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
            Channel channel = bootstrap.connect(host(), port()).sync().channel();
            channel.closeFuture().addListener(future -> closeReconnectListener()).sync();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }

    public void closeReconnectListener() {
        // 断线重连
        new Thread(new RetryConnect()).start();
    }

    public final class RetryConnect implements Runnable {
        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
            while (true) {
                try {
                    log.info("try reconnect......");
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
}
