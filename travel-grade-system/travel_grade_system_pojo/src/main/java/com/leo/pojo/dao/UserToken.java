package com.leo.pojo.dao;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Date;

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