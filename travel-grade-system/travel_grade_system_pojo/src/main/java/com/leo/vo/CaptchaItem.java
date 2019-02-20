package com.leo.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 验证码类
 */
@Data
public class CaptchaItem {

    private String phoneNumber;
    private String code;
    private LocalDateTime expireTime;
}
