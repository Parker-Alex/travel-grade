package com.leo.service;

import com.leo.dto.TravelUserRelCustom;
import com.leo.pojo.TravelUserRel;

import java.util.List;

/**
 * @ClassName IUserRelService
 * @Description 用户与用户关系相关操作接口类
 * @Author li.jiawei
 * @Date 2019/4/18 15:33
 * @Version 1.0
 */
public interface IUserRelService {

    /**
     * @Author li.jiawei
     * @Description 得到用户的粉丝信息列表
     * @Date 15:55 2019/4/18
     */
    List<TravelUserRel> getFans(String userId);

    /**
     * @Author li.jiawei
     * @Description 得到用户的关注用户数
     * @Date 3:00 2019/5/4
     */
    int getFollow(String userId);

    /**
     * @Author li.jiawei
     * @Description 获得用户关注用户列表
     * @Date 15:22 2019/5/4
     */
    List<TravelUserRelCustom> getMyFollows(String userId);

    /**
     * @Author li.jiawei
     * @Description 获得用户的粉丝用户列表
     * @Date 15:22 2019/5/4
     */
    List<TravelUserRelCustom> getMyFans(String userId);
}
