package com.leo.service;

import com.leo.pojo.TravelUser;

import java.util.List;

public interface IUserService {

    TravelUser getUserByOpenId(String openId);

    int addUser(TravelUser user);

    List<TravelUser> getUsersByUsername(String username);

}
