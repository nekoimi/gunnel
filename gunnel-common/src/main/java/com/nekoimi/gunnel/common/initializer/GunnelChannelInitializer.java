package com.nekoimi.gunnel.common.initializer;

import com.nekoimi.gunnel.common.codec.GunnelMessageDecoder;
import com.nekoimi.gunnel.common.codec.GunnelMessageEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * nekoimi  2021/8/14 21:02
 */
public abstract class GunnelChannelInitializer extends ChannelInitializer<SocketChannel> {
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
        // 追加逻辑处理
        channel0(pipeline);
    }

    abstract protected void channel0(ChannelPipeline pipeline);
}
