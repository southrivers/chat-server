package com.h3c.util;

import io.netty.channel.Channel;

import static com.h3c.Constant.LOGIN_STATUS;

public class Utils {

    /**
     * 判断用户对应的channel是否已经登录了
     *
     * @param channel
     * @return
     */
    public static boolean hasLogin(Channel channel) {
        Boolean login = channel.attr(LOGIN_STATUS).get();
        return login != null && login;
    }
}
