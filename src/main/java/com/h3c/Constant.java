package com.h3c;

import io.netty.util.AttributeKey;

public interface Constant {

    AttributeKey<Boolean> LOGIN_STATUS = AttributeKey.newInstance("login");


    AttributeKey<String> USER_NAME = AttributeKey.newInstance("userName");
}
