package com.h3c.packet.impl;

import com.h3c.packet.Packet;
import lombok.Data;

import static com.h3c.packet.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet {
    String userName;

    String password;


    @Override
    public byte getCommand() {
        return LOGIN_REQUEST;
    }
}
