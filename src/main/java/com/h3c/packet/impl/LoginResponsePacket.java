package com.h3c.packet.impl;

import com.h3c.packet.Packet;
import lombok.Data;

import static com.h3c.packet.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet {

    Boolean login;

    String reason;

    @Override
    public byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
