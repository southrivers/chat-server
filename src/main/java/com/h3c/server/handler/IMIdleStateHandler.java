package com.h3c.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 服务端检测客户端心跳的Handler，需要放到pipeline的最前面以便能够接收到所有的请求，而且这个类实
 * 是会接收所有发送到服务端的消息的。
 */
public class IMIdleStateHandler extends IdleStateHandler {

    public IMIdleStateHandler() {
        /*
         * 这里如果不调用super方法来实现对象的构造，编译器会报错，原因是因为父类的构造方法是private或者protected的
         */
        super(15, 0, 0, TimeUnit.SECONDS);
    }

    /**
     * 在上面构造方法中指定了超时的事件参数，如果满足超时的参数，就会触发channelIdle的方法被调用
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        System.out.println("没有读取到客户端发送过来的消息，因此关闭这个客户端");
        ctx.channel().close();
    }
}
