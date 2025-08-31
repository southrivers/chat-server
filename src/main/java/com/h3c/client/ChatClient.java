package com.h3c.client;

import com.h3c.client.handler.LoginResponseHandler;
import com.h3c.client.handler.MessageResponseHandler;
import com.h3c.packet.impl.LoginRequestPacket;
import com.h3c.packet.impl.MessageRequestPacket;
import com.h3c.server.handler.PacketDecoder;
import com.h3c.server.handler.PacketEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.net.InetSocketAddress;
import java.util.Scanner;

import static com.h3c.Constant.LOGIN_STATUS;

public class ChatClient {

    public static void main(String[] args) throws InterruptedException {

        NioEventLoopGroup workers = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(workers)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        // size的偏移量和长度
                        ch.pipeline().addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 1, 4));
                        ch.pipeline().addLast(new PacketDecoder());
                        ch.pipeline().addLast(new LoginResponseHandler());
                        ch.pipeline().addLast(new MessageResponseHandler());
                        ch.pipeline().addLast(new PacketEncoder());
                    }
                });
        ChannelFuture sync = bootstrap.connect(new InetSocketAddress(9000));
        sync.addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {

                // TODO 待测试 这里需要开启一个新的线程才可以，否则这里是使用的eventloop中的线程，这样event就会在这里循环下去，无法接收来自服务端的消息
                new Thread(() -> {
                    while (true) {
                        try {
                            Channel channel = ((ChannelFuture) future).channel();
                            Scanner scanner = new Scanner(System.in);
                            String nextLine = scanner.nextLine();
                            // 这里要放到scanner后面，这样才能实时的获取到对应的状态数据
                            Boolean loginStatus = channel.attr(LOGIN_STATUS).get();
                            if (null != loginStatus && loginStatus) {
                                if (channel.isActive() && channel.isWritable()) {
                                    for (int i = 0; i < 100; i++) {
                                        MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
                                        messageRequestPacket.setMessage(nextLine + i);
                                        channel.writeAndFlush(messageRequestPacket);
                                    }
                                } else {
                                    break;
                                }
                            } else {
                                System.out.println("还没有登陆。。。。。");
                                LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
                                String[] split = nextLine.split(",");
                                loginRequestPacket.setUserName(split[0]);
                                loginRequestPacket.setPassword(split[1]);
                                channel.writeAndFlush(loginRequestPacket);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        sync.channel().closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                workers.shutdownGracefully();
            }
        });
    }
}
