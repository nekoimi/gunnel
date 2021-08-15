package com.nekoimi.gunnel.common.codec;

import com.nekoimi.gunnel.common.constants.SystemConstants;
import com.nekoimi.gunnel.common.protocol.GunnelMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.nio.charset.StandardCharsets;

/**
 * nekoimi  2021/8/14 14:17
 * <p>
 * Gunnel 消息编码器
 *
 * 数据格式查看解码器
 * @see GunnelMessageDecoder
 */
@Slf4j
public class GunnelMessageEncoder extends MessageToByteEncoder<GunnelMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, GunnelMessage msg, ByteBuf out) throws Exception {
        log.debug("------------------------ GunnelMessageEncoder BEGIN ------------------------");
        try (
                ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                DataOutputStream dataOutput = new DataOutputStream(byteOutput);
        ) {
            dataOutput.write(msg.getMessage().toJson().getBytes(StandardCharsets.UTF_8));

            byte[] bytes = byteOutput.toByteArray();
            // 写入版本号
            out.writeDouble(SystemConstants.VERSION);
            log.debug(">>>>>>> version: " + SystemConstants.VERSION);
            // 写入消息类型
            out.writeInt(msg.getType().getCode());
            log.debug(">>>>>>> msgType: " + msg.getType());
            // 写入长度
            out.writeInt(bytes.length);
            log.debug(">>>>>>> dataLength: " + bytes.length);
            // 写入数据
            out.writeBytes(bytes);
            log.debug(">>>>>>> data: " + msg.getMessage().toJson());
        }

        log.debug("------------------------ GunnelMessageEncoder BEGIN ------------------------");
    }
}
