package com.leo.service;

import com.leo.dto.TravelCommentCustom;
import com.leo.pojo.TravelComment;

/**
 * @ClassName ICommentService
 * @Description TODO
 * @Author li.jiawei
 * @Date 2019/3/23 23:53
 * @Version 1.0
 */
public interface ICommentService {

    int insertComment(TravelComment comment);

    TravelCommentCustom getCommentByUnion(String commentId);
}
