package com.h3c.packet;

public interface Command {

    byte LOGIN_REQUEST = 0;

    byte LOGIN_RESPONSE = 1;

    byte MESSAGE_REQUEST = 2;

    byte MESSAGE_RESPONSE = 3;

    byte HEARTBEAT_REQUEST = 4;

    byte HEARTBEAT_RESPONSE = 5;
}
