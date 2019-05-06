package com.leo.mapper;

import com.leo.dto.TravelCityCustom;
import com.leo.pojo.TravelCity;
import com.leo.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TravelCityMapper extends MyMapper<TravelCity> {

//    得到所有城市的名字
    List<String> getAllName();
    
    /**
     * @Author li.jiawei
     * @Description 获得用户去过的所有城市列表
     * @Date 0:04 2019/4/19
     */
    List<TravelCity> userGoneCities(@Param("userId") String userId);

    /**
     * @Author li.jiawei
     * @Description 获得用户想去城市列表
     * @Date 0:16 2019/4/19
     */
    List<TravelCity> userLikeCities(@Param("userId") String userId);

    /**
     * @Author li.jiawei
     * @Description 获得用户点赞城市列表
     * @Date 2:14 2019/5/4
     */
    List<TravelCity> userFavourCities(@Param("userId") String userId);

    /**
     * @Author li.jiawei
     * @Description 获得用户推荐城市列表
     * @Date 15:26 2019/4/21
     */
    List<TravelCity> userRecommendCities(@Param("userId") String userId);
}