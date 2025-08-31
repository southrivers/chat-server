package com.h3c.server.handler;

import com.h3c.packet.impl.MessageRequestPacket;
import com.h3c.packet.impl.MessageResponsePacket;
import com.h3c.util.Utils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.h3c.Constant.USER_NAME;

/**
 *
 * ctx.write的时候是从当前handler向前找，ctx.channel.write是从pipeline的tail向前找，在使用的时候可以结合pipeline中handler的顺序来确保消息被正确的发送
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        String toUserName = msg.getToUserName();
        Channel channel = Utils.map.get(toUserName);
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        String fromUserName = ctx.channel().attr(USER_NAME).get();
        messageResponsePacket.setFromUserName(fromUserName);
        messageResponsePacket.setMessage("收到客户端发过来的消息：" +  msg.getMessage());
        channel.writeAndFlush(messageResponsePacket);
    }
}
