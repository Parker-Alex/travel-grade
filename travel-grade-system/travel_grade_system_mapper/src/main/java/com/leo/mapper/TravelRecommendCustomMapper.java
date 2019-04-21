package com.leo.mapper;

import com.leo.pojo.TravelRecommend;
import com.leo.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TravelRecommendCustomMapper extends MyMapper<TravelRecommend> {

    /**
     * @Author li.jiawei
     * @Description 获得用户的所有推荐信息
     * @Date 15:58 2019/4/18
     */
    List<TravelRecommend> getUserRecommends(@Param("userId") String userId);

}