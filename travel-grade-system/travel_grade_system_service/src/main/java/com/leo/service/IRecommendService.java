package com.leo.service;

import com.leo.pojo.TravelRecommend;

import java.util.List;

/**
 * @ClassName IRecommendService
 * @Description 用户推荐类相关操作接口类
 * @Author li.jiawei
 * @Date 2019/4/18 15:55
 * @Version 1.0
 */
public interface IRecommendService {

    /**
     * @Author li.jiawei
     * @Description 获得用户的所有推荐信息
     * @Date 15:56 2019/4/18
     */
    List<TravelRecommend> getUserRecommends(String userId);
}
