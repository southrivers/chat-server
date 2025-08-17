package com.h3c.server.handler;

import com.h3c.packet.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import static com.h3c.serial.Serial.DEFAULT_SERIAL;

/**
 * 原本需要在一个handler中包含解码、处理数据、编码的过程，现在拆分成多个handler了
 * 解码：原本需要调用super的方法确保完整，这里就不用调super方法了，只需要添加到list中就能向后传递
 * 数据处理：原本会根据数据类型来进行处理，不符合的类型需要调用ctx对应的方法来向后传播，现在就不用了
 * 编码：除了需要自己创建buffer好像也没有其他的改变
 */
public class PacketDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        Packet decode = DEFAULT_SERIAL.decode(byteBuf);
        // 只需要添加到list中
        list.add(decode);
    }
}
