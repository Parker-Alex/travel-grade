package com.leo.service;

import com.github.pagehelper.PageInfo;
import com.leo.pojo.TravelUser;

import java.util.List;

public interface IUserService {

    int addUser(TravelUser user);

    TravelUser getUserByOpenId(String openId);

    List<TravelUser> getUsersByOpenId(String openId);

    List<TravelUser> getUsersByUsername(String username);

    List<TravelUser> getUsersByMobile(String mobile);

    TravelUser getUserByUserId(String userId);
    
    /**
     * @Author li.jiawei
     * @Description 后台得到所有用户方法
     * @Date 2:14 2019/5/2
     */
    PageInfo<TravelUser> getAllUsersByAdmin(Integer pageNum, Integer pageSize);

    /**
     * @Author li.jiawei
     * @Description 后台停用用户方法
     * @Date 2:47 2019/5/2
     */
    int stopUser(String userId);

    /**
     * @Author li.jiawei
     * @Description 后台恢复用户方法
     * @Date 2:59 2019/5/2
     */
    int resumeUser(String userId);
}
