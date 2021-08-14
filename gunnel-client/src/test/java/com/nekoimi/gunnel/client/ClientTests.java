package com.nekoimi.gunnel.client;

import com.nekoimi.gunnel.common.codec.GunnelMessageDecoder;
import com.nekoimi.gunnel.common.codec.GunnelMessageEncoder;
import com.nekoimi.gunnel.common.enums.MsgType;
import com.nekoimi.gunnel.common.protocol.GunnelMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.junit.Test;

/**
 * nekoimi  2021/8/14 23:23
 */
public class ClientTests {

    @Test
    public void testSendMessage() {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        // Netty的TCP分包处理器
                        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 12, 4, 0, 0));
                        // 自定义协议编码解码器
                        pipeline.addLast(new GunnelMessageDecoder());
                        pipeline.addLast(new GunnelMessageEncoder());
                    }
                });
        try {
            Channel channel = bootstrap.connect("192.168.3.3", 9933).sync().channel();
            channel.closeFuture().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    workerGroup.shutdownGracefully();
                }
            });

            GunnelMessage message = GunnelMessage.builder().message("hello world").type(MsgType.GU_DATA).build();

            channel.writeAndFlush(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
