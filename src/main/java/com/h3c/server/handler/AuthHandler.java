package com.h3c.server.handler;

import com.h3c.util.Utils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 单纯的客户端校验是不够的，还需要服务端校验通过才可以，不然的话客户端知道这个协议之后可以构造一个正常的消息发送给服务端。
 * 不过服务端在完成校验之后，需要把这段是否登录的判断逻辑删除。
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        if (Utils.hasLogin(channel)) {
            ctx.pipeline().remove(this);
            // 需要向后传播消息
            super.channelRead(ctx, msg);
        } else {
            channel.close();
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if (Utils.hasLogin(ctx.channel())) {
            System.out.println("用户已经登陆了，后续不需要再进行登录的判断");
        } else {
            System.out.println("用户没有登录，后续处理是没必要的，因此关闭了客户端");
        }
    }
}
