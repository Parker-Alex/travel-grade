package com.leo.mapper;

import com.leo.pojo.TravelUserCityRel;
import com.leo.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

public interface TravelUserCityRelMapper extends MyMapper<TravelUserCityRel> {

    /**
     * @Author li.jiawei
     * @Description 通过操作类型得到人数，0 => 想去，1 => 点赞，2 => 去过
     * @Date 19:49 2019/3/23
     */
    int getCountByType(@Param("type") int type, @Param("cityId") String cityId);

    /**
     * @Author li.jiawei
     * @Description 通过用户不为0的评分，计算城市的平均评分
     * @Date 1:42 2019/4/17
     */
    Double getAvgGrade(@Param("cityId") String cityId);
}