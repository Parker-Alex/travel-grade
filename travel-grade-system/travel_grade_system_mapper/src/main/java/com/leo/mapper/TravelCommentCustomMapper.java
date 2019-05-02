package com.leo.mapper;

import com.leo.dto.TravelCityCustom;
import com.leo.dto.TravelCommentCustom;
import com.leo.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TravelCommentCustomMapper extends MyMapper<TravelCommentCustom> {

    /**
     * @Author li.jiawei
     * @Description 通过评论id联表查询评论信息以及用户和回复用户信息
     * @Date 23:02 2019/3/24
     */
    TravelCommentCustom getCommentByUnion(@Param("id") String commentId);

    /**
     * @Author li.jiawei
     * @Description 分页获得该城市下所有的评论
     * @Date 20:18 2019/3/25
     */
    List<TravelCommentCustom> getAllComment(@Param("id") String cityId);

    /**
     * @Author li.jiawei
     * @Description 获得某条评论下的所有子评论
     * @Date 17:59 2019/4/9
     */
    List<TravelCommentCustom> moreComment(@Param("id") String commentId, @Param("cityId") String cityId);

    /**
     * @Author li.jiawei
     * @Description 获得用户的所有评论
     * @Date 15:50 2019/4/18
     */
    List<TravelCommentCustom> getUserComments(@Param("userId") String userId);
    
    /**
     * @Author li.jiawei
     * @Description 后台获取系统所有评论
     * @Date 1:09 2019/5/2
     */
    List<TravelCommentCustom> getAllCommentsByAdmin();
}