package com.nekoimi.gunnel.server.net.initializer;

import com.nekoimi.gunnel.server.codec.GunnelMessageDecoder;
import com.nekoimi.gunnel.server.codec.GunnelMessageEncoder;
import com.nekoimi.gunnel.server.handler.GunnelServerHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * nekoimi  2021/8/14 17:16
 */
public class GunnelServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // Netty的TCP分包处理器
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
        // Idle 心跳监测机制
        pipeline.addLast(new IdleStateHandler(60, 30, 0));
        // 自定义协议编码解码器
        pipeline.addLast(new GunnelMessageDecoder());
        pipeline.addLast(new GunnelMessageEncoder());
        // Gunnel逻辑实现处理
        pipeline.addLast(new GunnelServerHandler());
    }
}
