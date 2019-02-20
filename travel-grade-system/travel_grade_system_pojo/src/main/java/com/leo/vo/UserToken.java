package com.leo.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户令牌类
 */
@Data
public class UserToken {

    private String userId;

    private String token;

    private String sessionKey;

    private LocalDateTime expireTime;

    private LocalDateTime updateTime;

}