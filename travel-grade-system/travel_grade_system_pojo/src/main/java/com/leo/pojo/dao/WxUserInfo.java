package com.leo.pojo.dao;

import lombok.Data;

/**
 * 封装微信传输的用户信息类
 */
@Data
public class WxUserInfo {
    private String code;
    private UserInfo userInfo;
}
