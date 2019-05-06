package com.leo.mapper;

import com.leo.dto.TravelUserRelCustom;
import com.leo.pojo.TravelUserRel;
import com.leo.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TravelUserRelCustomMapper extends MyMapper<TravelUserRel> {

    /**
     * @Author li.jiawei
     * @Description 获得用户关注用户列表
     * @Date 15:20 2019/5/4
     */
    List<TravelUserRelCustom> getMyFollows(@Param("userId") String userId);

    /**
     * @Author li.jiawei
     * @Description 获得用户的粉丝用户列表
     * @Date 15:21 2019/5/4
     */
    List<TravelUserRelCustom> getMyFans(@Param("userId") String userId);
}