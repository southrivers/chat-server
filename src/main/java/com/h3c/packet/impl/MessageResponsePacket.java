package com.h3c.packet.impl;

import com.h3c.packet.Packet;
import lombok.Data;

import static com.h3c.packet.Command.MESSAGE_RESPONSE;

@Data
public class MessageResponsePacket extends Packet {

    String fromUserName;

    String message;

    @Override
    public byte getCommand() {
        return MESSAGE_RESPONSE;
    }
}
