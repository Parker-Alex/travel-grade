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

    // 添加评论，并且返回评论id
    String insertComment(String request, String userId);

    TravelCommentCustom getCommentByUnion(String commentId);

    List<TravelCommentCustom> getAllComment(String cityId, Integer pageIndex, Integer pageSize);

    int deleteComment(String commentId, String userId);
}
