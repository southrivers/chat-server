package com.h3c.server.handler;

import com.h3c.packet.impl.LoginRequestPacket;
import com.h3c.packet.impl.LoginResponsePacket;
import com.h3c.util.Utils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static com.h3c.Constant.LOGIN_STATUS;
import static com.h3c.Constant.USER_NAME;

/**
 * 说明这里是只有匹配的消息（泛型）才会进到对应的方法，不匹配的消息会继续向后传播，这个用通用的channelhandler也可以实现，只是需要用if else（包含向后处理的逻辑）
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        System.out.println(msg);
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        if ((msg.getUserName().equals("wangershuai") || msg.getUserName().equals("wangsanshuai")) && msg.getPassword().equals("password")) {
            loginResponsePacket.setLogin(true);
            loginResponsePacket.setReason("用户名密码正确");
            ctx.channel().attr(LOGIN_STATUS).set(true);
            // 方便下次根据用户名检索对应的channel
            ctx.channel().attr(USER_NAME).set(msg.getUserName());
            Utils.map.put(msg.getUserName(), ctx.channel());
        } else {
            loginResponsePacket.setLogin(false);
            loginResponsePacket.setReason("用户名密码错误");
        }
        // 这里会从pipeline的尾部依次遍历handler来完成消息的发送
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    /**
     * 用户登出操作需要对map进行清理，防止内存泄漏
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        super.channelInactive(ctx);
    }
}
