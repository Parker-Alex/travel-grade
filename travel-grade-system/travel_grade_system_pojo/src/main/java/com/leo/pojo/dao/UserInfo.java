package com.leo.pojo.dao;

import lombok.Data;

/**
 * 微信用户信息类，与微信的个人信息对应
 */
@Data
public class UserInfo {

    private String nickName;
    private String avatarUrl;
    private String country;
    private String province;
    private String city;
    private String language;
    private Byte gender;
}
