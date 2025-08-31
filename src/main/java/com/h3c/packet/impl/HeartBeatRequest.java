package com.h3c.packet.impl;

import com.h3c.packet.Packet;

import static com.h3c.packet.Command.HEARTBEAT_REQUEST;

public class HeartBeatRequest extends Packet {
    @Override
    public byte getCommand() {
        return HEARTBEAT_REQUEST;
    }
}
