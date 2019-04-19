package com.leo.service;

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
}
