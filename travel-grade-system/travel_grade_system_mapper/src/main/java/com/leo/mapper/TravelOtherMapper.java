package com.leo.mapper;

import com.leo.pojo.TravelOther;
import com.leo.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TravelOtherMapper extends MyMapper<TravelOther> {

    /**
     * @Author li.jiawei
     * @Description 按服务类型得到城市的其他服务评分
     * @Date 19:45 2019/4/23
     */
    Double getOtherGrade(@Param("cityId") String cityId, @Param("type") int type);

}