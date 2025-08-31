package com.h3c.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class Spliter extends LengthFieldBasedFrameDecoder {
    public Spliter(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    /**
     * 在第一个decoder中对报文的合法性进行校验
     *
     * @param ctx             the {@link ChannelHandlerContext} which this {@link ByteToMessageDecoder} belongs to
     * @param in              the {@link ByteBuf} from which to read data
     * @return
     * @throws Exception
     */
    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in.readInt() != 12) {
            // 校验报文的合法性，如果非法则直接关闭
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }
}
