package com.leo.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TravelOtherEnum
 * @Description TODO
 * @Author li.jiawei
 * @Date 2019/4/22 15:16
 * @Version 1.0
 */
public enum TravelOtherEnum {

    FRUITS(0, "水果"),
    TRAFFIC(1, "交通"),
    WEATHER(2, "天气"),
    CATE(3, "美食"),
    STAY(4, "住宿");

    private int code;
    private String type;

    TravelOtherEnum(int code, String type) {
        this.code = code;
        this.type = type;
    }

    public static int getMyCode(String type) {
        for (TravelOtherEnum otherEnum : values()) {
            if (otherEnum.getType().equals(type)) {
                otherEnum.getCode();
            }
        }
        return 0;
    }

    public static String getMyType(int code) {
        for (TravelOtherEnum otherEnum : values()) {
            if (otherEnum.getCode() == code) {
                otherEnum.getType();
            }
        }
        return null;
    }

    public static List<String> getAllType() {
        List<String> types = new ArrayList<>();
        for (TravelOtherEnum otherEnum : values()) {
            types.add(otherEnum.getType());
        }
        return types;
    }

    public int getCode() {
        return code;
    }

    public String getType() {
        return type;
    }}
