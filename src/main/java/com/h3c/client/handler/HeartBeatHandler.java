package com.h3c.client.handler;

import com.h3c.packet.impl.HeartBeatRequest;
import com.h3c.packet.impl.LoginRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        /**
         * 使用nio线程定期执行心跳任务
         */


        super.channelActive(ctx);
    }
}
