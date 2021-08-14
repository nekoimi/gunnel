package com.nekoimi.gunnel.server.codec;

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
 * 服务器端消息编码器
 */
@Slf4j
public class GunnelMessageEncoder extends MessageToByteEncoder<GunnelMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, GunnelMessage msg, ByteBuf out) throws Exception {
        log.debug("------------------------ GunnelMessageEncoder BEGIN ------------------------");
        log.debug(msg.toString());
        try (
                ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
                DataOutputStream dataOutput = new DataOutputStream(byteOutput);
        ) {
            dataOutput.writeInt(msg.getType().getCode());
            dataOutput.write(msg.getMessage().toString().getBytes(StandardCharsets.UTF_8));

            byte[] bytes = byteOutput.toByteArray();
            out.writeInt(bytes.length);
            out.writeBytes(bytes);
        }

        log.debug("------------------------ GunnelMessageEncoder BEGIN ------------------------");
    }
}
