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

    Double getAvgGrade(@Param("cityId") String cityId);
}