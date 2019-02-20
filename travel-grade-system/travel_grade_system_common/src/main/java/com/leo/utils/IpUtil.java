package com.leo.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * IP相关操作工具类
 */
public class IpUtil {

    public static String getIp(HttpServletRequest request) {
        String xff = request.getHeader("x-forwarded-for");
        if (xff == null) {
            xff = request.getRemoteAddr();
        }
        return xff;
    }
}
