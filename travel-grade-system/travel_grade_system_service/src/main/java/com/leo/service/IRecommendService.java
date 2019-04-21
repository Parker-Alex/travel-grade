package com.leo.service;

import com.leo.pojo.TravelCity;
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
    List<TravelCity> getUserRecommends(String userId);

    /**
     * @Author li.jiawei
     * @Description 添加推荐城市
     * @Date 0:47 2019/4/21
     */
    int addRecommend(String userId, String reason, String provinceName, String cityName, String httpPath);

    /**
     * @Author li.jiawei
     * @Description 计算用户的推荐城市数
     * @Date 13:26 2019/4/21
     */
    int getRecommendCount(String userId);
}
