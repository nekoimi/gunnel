package com.nekoimi.gunnel.common.codec;

import com.nekoimi.gunnel.common.enums.MsgType;
import com.nekoimi.gunnel.common.protocol.GunnelMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * nekoimi  2021/8/14 14:17
 * <p>
 * 服务器端消息解码器
 * <p>
 * 数据包格式:
 * +----------+----------+----------+----------------+
 * |   版本号  |  消息类型  |   长度   |     数据        |
 * +----------+----------+----------+----------------+
 * 1、服务版本号，double类型
 * 2、消息类型，int类型
 * 3、传输数据的长度，int类型
 * 4、要传输的数据
 */
@Slf4j
public class GunnelMessageDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        log.debug("------------------------ GunnelMessageDecoder BEGIN ------------------------");
        // 读取一个double数据 获取服务版本号
        double version = msg.readDouble();
        // 读取一个int数据，判断消息类型, ByteBuf 的读指针会先后移动4字节
        int msgTypeCode = msg.readInt();
        MsgType msgType = MsgType.valueOf(msgTypeCode);
        // 读取下一个int数据，确定需要读取的数据长度
        int length = msg.readInt();
        // 读取具体数据
        byte[] bytes = ByteBufUtil.getBytes(msg, msg.readerIndex(), length);

        ChannelId channelId = ctx.channel().id();
        String longId = channelId.asLongText();
        String shortId = channelId.asShortText();
        log.debug("ChannelId: " + longId + ", " + shortId);

        GunnelMessage message = GunnelMessage.builder().channelId(longId).type(msgType).message(new String(bytes)).data(bytes).build();

        log.debug(message.toString());

        out.add(message);

        log.debug("------------------------ GunnelMessageDecoder END ------------------------");
    }
}
