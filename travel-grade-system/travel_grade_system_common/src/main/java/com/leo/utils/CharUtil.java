package com.leo.utils;

import java.util.Random;

/**
 * 生成验证码工具类
 */
public class CharUtil {

    public static String genetateRandomString(Integer num) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        return getString(num, base);
    }

    public static String generateRandomNum(Integer num) {
        String base = "0123456789";
        return getString(num, base);
    }

    private static String getString(Integer num, String base) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < num; i++) {
            int index = random.nextInt(base.length());
            sb.append(base.charAt(index));
        }
        return sb.toString();
    }
}
