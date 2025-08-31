package com.h3c.server.handler;

import com.h3c.packet.impl.HeartBeatRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequest> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequest msg) throws Exception {
        System.out.println("收到客户端心跳");
    }
}
