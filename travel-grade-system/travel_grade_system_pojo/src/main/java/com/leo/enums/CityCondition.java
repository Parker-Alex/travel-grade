package com.leo.enums;

/**
 * @ClassName CityCondition
 * @Description 城市查询条件枚举类，0=>按评分，1=>点赞，2=>想去，3=>去过
 * @Author li.jiawei
 * @Date 2019/4/14 17:45
 * @Version 1.0
 */
public enum CityCondition {

    GRADE(0, "grade"),
    FAVOUR(1, "favour_count"),
    LIKE(2, "like_count"),
    GONE(3, "gone_count");

    private int code;
    private String property;

    CityCondition(int code, String property) {
        this.code = code;
        this.property = property;
    }

//    通过条件码返回对应数据库字段
    public static String getProperty(int code) {
        for (CityCondition cityCondition : values()) {
            if (cityCondition.getCode() == code) {
                return cityCondition.getProperty();
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getProperty() {
        return property;
    }
}
