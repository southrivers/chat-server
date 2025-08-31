package com.h3c.util;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

import static com.h3c.Constant.LOGIN_STATUS;

public class Utils {

    /**
     * 存放用户名和channel的映射
     */
    public static Map<String, Channel> map = new HashMap<>();

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
