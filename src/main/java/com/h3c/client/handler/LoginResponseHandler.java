package com.h3c.client.handler;

import com.h3c.packet.impl.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.h3c.Constant.LOGIN_STATUS;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        System.out.println(msg);
        boolean status = msg.getLogin();
        if (status) {
            System.out.println("登录成功。。。。。");
            // 在服务端设置客户端的attr并不会体现在客户端的attr里面，因为这是两个不同的JVM
            ctx.channel().attr(LOGIN_STATUS).set(true);
        } else {
            System.out.println("登录失败。。。。。");
        }
    }
}
