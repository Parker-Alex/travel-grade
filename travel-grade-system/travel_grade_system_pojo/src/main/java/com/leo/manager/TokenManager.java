package com.leo.manager;

import com.leo.vo.UserToken;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 用户登录令牌管理类
 */
public class TokenManager {

//    token -> userToken
    private static final Map<String, UserToken> tokenMap = new HashMap<>();
//    userId -> userToken
    private static final Map<String, UserToken> idMap = new HashMap<>();

//    通过token得到用户id
    public static String getUserId(String token) {
        UserToken userToken = tokenMap.get(token);
        if (userToken == null) {
            return null;
        }
//        如果已经过期
        if (userToken.getExpireTime().isBefore(LocalDateTime.now())) {
            tokenMap.remove(token);
            idMap.remove(userToken.getUserId());
            return null;
        }
        return userToken.getUserId();
    }

//    通过用户id生成token
    public static UserToken generateToken(String userId) {
        UserToken userToken = new UserToken();

        String token = UUID.randomUUID().toString();
        while(tokenMap.containsKey(token)) {
            token = UUID.randomUUID().toString();
        }

        LocalDateTime update = LocalDateTime.now();
        LocalDateTime expire = update.plusDays(1);

        userToken.setUserId(userId);
        userToken.setToken(token);
        userToken.setUpdateTime(update);
        userToken.setExpireTime(expire);

        tokenMap.put(token, userToken);
        idMap.put(userId, userToken);

        return userToken;
    }

//    通过用户id得到session
    public static String getSessionKey(String userId) {
        UserToken userToken = idMap.get(userId);
        if (userToken == null) {
            return null;
        }
//        如果已经过期
        if (userToken.getExpireTime().isBefore(LocalDateTime.now())) {
            idMap.remove(userId);
            tokenMap.remove(userToken.getToken());
            return null;
        }
        return userToken.getSessionKey();
    }

//    通过用户id移除token
    public static void removeToken(String userId) {
        UserToken userToken = idMap.get(userId);
        idMap.remove(userId);
        tokenMap.remove(userToken.getToken());
    }
}
