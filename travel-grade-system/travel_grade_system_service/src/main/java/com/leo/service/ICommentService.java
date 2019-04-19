package com.leo.service;

import com.leo.dto.TravelCommentCustom;
import com.leo.pojo.TravelComment;

import java.util.List;

/**
 * @ClassName ICommentService
 * @Description TODO
 * @Author li.jiawei
 * @Date 2019/3/23 23:53
 * @Version 1.0
 */
public interface ICommentService {

//    添加评论，并且返回评论id
    String insertComment(String request, String userId);

    TravelCommentCustom getCommentByUnion(String commentId);

    List<TravelCommentCustom> getAllComment(String cityId, Integer pageIndex, Integer pageSize);

    int deleteComment(String commentId, String userId);

    /**
     * @Author li.jiawei
     * @Description 获得子评论方法
     * @Date 17:54 2019/4/9
     */
    List<TravelCommentCustom> moreComment(String body);

    /**
     * @Author li.jiawei
     * @Description 得到用户的所有评论信息
     * @Date 15:45 2019/4/18
     */
    List<TravelCommentCustom> getUserComments(String userId);
}
