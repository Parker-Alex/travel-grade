package com.leo.utils;

/**
 * 分析性别工具类
 */
public class GenderAnalyze {

    public static int analyze(String gender) {
        if ("男".equals(gender)) {
            return 1;
        } else {
            return 0;
        }
    }
}
