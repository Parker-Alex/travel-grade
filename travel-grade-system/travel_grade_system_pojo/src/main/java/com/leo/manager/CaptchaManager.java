package com.leo.manager;

import com.leo.vo.CaptchaItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证码管理类
 */
public class CaptchaManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(CaptchaManager.class);

//    自定义缓存
    private static final Map<String, CaptchaItem> cache = new HashMap();

    public static boolean addToCache(String phoneNumber, String code) {

//        存在验证码对象
        if (cache.get(phoneNumber) != null ) {
//            验证码没有过期，则不用保存
            if (cache.get(phoneNumber).getExpireTime().isAfter(LocalDateTime.now())) {
                return false;
            } else {
//                验证码过期，进行删除
                cache.remove(phoneNumber);
                LOGGER.info("从自定义缓存中删除验证码，缓存剩余容量：" + cache.size());
            }
        }

//        创建验证码对象，保存相关信息
        CaptchaItem captchaItem = new CaptchaItem();
        captchaItem.setCode(code);
        captchaItem.setPhoneNumber(phoneNumber);
//        1分钟有效期
        captchaItem.setExpireTime(LocalDateTime.now().plusMinutes(1));

//        存入缓存中
        cache.put(phoneNumber, captchaItem);
        LOGGER.info("成功将验证码加入自定义缓存：" + phoneNumber + "=>" + captchaItem);

        return true;
    }

    public static String getCaptcha(String phoneNumber) {

//        获得验证码类
        CaptchaItem captchaItem = cache.get(phoneNumber);

//        没有该验证码类
        if (captchaItem == null) {
            return null;
        }

//        该验证码类过期
        if (captchaItem.getExpireTime().isBefore(LocalDateTime.now())) {
            return null;
        }

        return captchaItem.getCode();
    }
}
