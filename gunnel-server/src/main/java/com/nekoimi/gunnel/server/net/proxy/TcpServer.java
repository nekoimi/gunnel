package com.nekoimi.gunnel.server.net.proxy;

import com.nekoimi.gunnel.server.net.AbstractServer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * nekoimi  2021/8/13 23:32
 */
public class TcpServer extends AbstractServer {
    @Override
    public ChannelInitializer<SocketChannel> initializer() {
        return new ChannelInitializer<>() {
            @Override
            protected void initChannel(SocketChannel channel) throws Exception {
                ChannelPipeline pipeline = channel.pipeline();
                pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));

                pipeline.addLast(new StringDecoder());
                pipeline.addLast(new StringEncoder());
            }
        };
    }
}
