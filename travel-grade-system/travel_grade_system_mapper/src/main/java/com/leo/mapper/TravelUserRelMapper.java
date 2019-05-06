package com.leo.mapper;

import com.leo.pojo.TravelUserRel;
import com.leo.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TravelUserRelMapper extends MyMapper<TravelUserRel> {

    /**
     * @Author li.jiawei
     * @Description 获得用户的粉丝信息列表
     * @Date 15:39 2019/4/18
     */
    List<TravelUserRel> getFans(@Param("id") String userId);

    /**
     * @Author li.jiawei
     * @Description 获得用户的关注用户数
     * @Date 2:59 2019/5/4
     */
    int getFollow(@Param("id") String userId);
}