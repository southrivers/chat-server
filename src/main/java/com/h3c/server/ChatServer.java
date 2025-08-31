package com.h3c.server;

import com.h3c.server.handler.*;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class ChatServer {
    public static void main(String[] args) throws InterruptedException {

        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new IMIdleStateHandler());
                        nioSocketChannel.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 1, 4));
                        // handler中直接调用ctx.write方法的时候需要把encoder放到pipeline的最前面
//                        nioSocketChannel.pipeline().addLast(new PacketEncoder());
                        nioSocketChannel.pipeline().addLast(new PacketDecoder());
                        nioSocketChannel.pipeline().addLast(new LoginRequestHandler());
                        nioSocketChannel.pipeline().addLast(new AuthHandler());
                        nioSocketChannel.pipeline().addLast(new MessageRequestHandler());
                        nioSocketChannel.pipeline().addLast(new PacketEncoder());
                    }
                });
        ChannelFuture sync = serverBootstrap.bind(9000).sync();
        sync.channel().closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                boss.shutdownGracefully();
                worker.shutdownGracefully();
            }
        });
    }
}
