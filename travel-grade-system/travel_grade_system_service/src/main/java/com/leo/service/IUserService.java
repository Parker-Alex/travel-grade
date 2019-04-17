package com.leo.service;

import com.leo.pojo.TravelUser;

import java.util.List;

public interface IUserService {

    int addUser(TravelUser user);

    TravelUser getUserByOpenId(String openId);

    List<TravelUser> getUsersByOpenId(String openId);

    List<TravelUser> getUsersByUsername(String username);

    List<TravelUser> getUsersByMobile(String mobile);

    TravelUser getUserByUserId(String userId);
}
