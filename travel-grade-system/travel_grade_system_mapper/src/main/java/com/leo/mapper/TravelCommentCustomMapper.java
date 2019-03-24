package com.leo.mapper;

import com.leo.dto.TravelCityCustom;
import com.leo.dto.TravelCommentCustom;
import com.leo.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

public interface TravelCommentCustomMapper extends MyMapper<TravelCommentCustom> {

    /**
     * @Author li.jiawei
     * @Description 通过评论id联表查询评论信息以及用户和回复用户信息
     * @Date 23:02 2019/3/24
     */
    TravelCommentCustom getCommentByUnion(@Param("id") String commentId);
}