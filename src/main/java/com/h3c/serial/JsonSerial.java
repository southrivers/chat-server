package com.h3c.serial;

import com.alibaba.fastjson2.JSON;
import com.h3c.packet.Packet;
import com.h3c.packet.impl.LoginRequestPacket;
import com.h3c.packet.impl.LoginResponsePacket;
import com.h3c.packet.impl.MessageRequestPacket;
import com.h3c.packet.impl.MessageResponsePacket;
import io.netty.buffer.ByteBuf;

import static com.h3c.packet.Command.*;

/**
 * 消息的序列化、反序列化方法
 */
public class JsonSerial implements Serial{

    public void encode(ByteBuf byteBuf, Packet packet) {
        byteBuf.writeByte(packet.getCommand());
        byte[] bytes = JSON.toJSONBytes(packet);
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    public Packet decode(ByteBuf msg) {
        byte command = msg.readByte();
        int length = msg.readInt();
        byte[] dst = new byte[length];
        msg.readBytes(dst);
        /**
         * 这里反序列化不需要确定的实现类么？
         */
        switch (command) {
            case LOGIN_REQUEST:
                return JSON.parseObject(dst, LoginRequestPacket.class);
            case LOGIN_RESPONSE:
                // 不能反序列化接口
                return JSON.parseObject(dst, LoginResponsePacket.class);
            case MESSAGE_REQUEST:
                return JSON.parseObject(dst, MessageRequestPacket.class);
            case MESSAGE_RESPONSE:
                return JSON.parseObject(dst, MessageResponsePacket.class);
            default:
                return null;
        }
    }
}
