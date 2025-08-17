package com.h3c.server.handler;

import com.h3c.packet.impl.MessageRequestPacket;
import com.h3c.packet.impl.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 *
 * ctx.write的时候是从当前handler向前找，ctx.channel.write是从pipeline的tail向前找，在使用的时候可以结合pipeline中handler的顺序来确保消息被正确的发送
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage("收到客户端发过来的消息：" +  msg.getMessage());
        ctx.channel().writeAndFlush(messageResponsePacket);
//        ctx.writeAndFlush(messageResponsePacket);
    }
}
