package com.leo.mapper;

import com.leo.pojo.TravelRecommend;
import com.leo.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TravelRecommendMapper extends MyMapper<TravelRecommend> {
    
    /**
     * @Author li.jiawei
     * @Description 得到用户的推荐城市数
     * @Date 13:29 2019/4/21
     */
    int getRecommendCount(@Param("userId") String userId);
}