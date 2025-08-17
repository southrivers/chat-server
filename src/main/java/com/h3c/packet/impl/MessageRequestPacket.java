package com.h3c.packet.impl;

import com.h3c.packet.Packet;
import lombok.Data;

import static com.h3c.packet.Command.MESSAGE_REQUEST;

@Data
public class MessageRequestPacket extends Packet {

    String message;

    @Override
    public byte getCommand() {
        return MESSAGE_REQUEST;
    }
}
