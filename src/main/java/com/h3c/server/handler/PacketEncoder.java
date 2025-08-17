package com.h3c.server.handler;

import com.h3c.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import static com.h3c.serial.Serial.DEFAULT_SERIAL;

/**
 * 针对packet进行编码
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> {

    /**
     *
     * @param ctx           the {@link ChannelHandlerContext} which this {@link MessageToByteEncoder} belongs to
     * @param msg           the message to encode
     * @param out           the {@link ByteBuf} into which the encoded message will be written
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        // 只需要把消息添加到out中就好，不用关心后续消息如何处理
        DEFAULT_SERIAL.encode(out, msg);
    }
}
