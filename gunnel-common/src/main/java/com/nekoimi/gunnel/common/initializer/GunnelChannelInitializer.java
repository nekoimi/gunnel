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
        /**
         * Netty的TCP分包处理器
         * >> TODO 粘包、半包初始配置，根据自定义的协议
         * @see com.nekoimi.gunnel.common.codec.GunnelMessageDecoder
         * >> TODO Length需要偏移两个字段（版本号 + 消息类型）
         * >> TODO Length 本身长度为4，不需要补长度，也不需要跳过某些字段
         * >> TODO 这样解码之后 ByteBuf 里面就包含全部的数据 => 版本号 + 消息类型 + 长度 + 数据
         */
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 12, 4, 0, 0));
        // Idle 空闲时间处理机制
        pipeline.addLast(new IdleStateHandler(60, 30, 0));
        // 自定义协议编码解码器
        pipeline.addLast(new GunnelMessageDecoder());
        pipeline.addLast(new GunnelMessageEncoder());
        // 追加逻辑处理
        channel0(pipeline);
    }

    abstract protected void channel0(ChannelPipeline pipeline);
}
