package com.nekoimi.gunnel.common.codec;

import com.nekoimi.gunnel.common.contract.Message;
import com.nekoimi.gunnel.common.enums.MsgType;
import com.nekoimi.gunnel.common.protocol.GunnelMessage;
import com.nekoimi.gunnel.common.utils.JsonUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * nekoimi  2021/8/14 14:17
 * <p>
 * Gunnel 消息解码器
 * <p>
 * 数据包格式:
 * +----------+----------+----------+----------------+
 * |   版本号  |  消息类型  |   长度   |     数据        |
 * +----------+----------+----------+----------------+
 * 1、服务版本号，double类型    8
 * 2、消息类型，int类型         4
 * 3、传输数据的长度，int类型    4
 * 4、要传输的数据
 */
@Slf4j
public class GunnelMessageDecoder extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        log.debug("------------------------ GunnelMessageDecoder BEGIN ------------------------");
        // 读取一个double数据 获取版本号 readerIndex + 8
        double version = msg.readDouble();
        log.debug("<<<<<<< version: " + version);
        // 读取一个int数据 获取消息类型 readerIndex + 4
        int msgTypeCode = msg.readInt();
        MsgType type = MsgType.valueOf(msgTypeCode);
        log.debug("<<<<<<< msgType: " + type);
        // 读取下一个int数据 获取数据长度 readerIndex + 4
        int length = msg.readInt();
        log.debug("<<<<<<< dataLength: " + length);
        // 读取具体数据
        CharSequence data = msg.readCharSequence(length, StandardCharsets.UTF_8);
        log.debug("<<<<<<< data: " + data);

        Message message = JsonUtils.parse(data.toString(), type.getMessageType());
        GunnelMessage gunnelMessage = GunnelMessage.builder().type(type).message(message).build();
        out.add(gunnelMessage);

        log.debug("------------------------ GunnelMessageDecoder END ------------------------");
    }
}
