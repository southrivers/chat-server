package com.h3c.client.handler;

import com.h3c.packet.impl.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * SimpleChannelInBoundHandler：处理特定类型的消息
 * ChannelInboundHandler：处理所有类型的消息
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception {
        System.out.println(msg);
    }
}
