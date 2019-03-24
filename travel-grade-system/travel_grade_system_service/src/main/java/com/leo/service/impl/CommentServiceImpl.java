package com.leo.service.impl;

import com.leo.dto.TravelCommentCustom;
import com.leo.mapper.TravelCommentCustomMapper;
import com.leo.mapper.TravelCommentMapper;
import com.leo.pojo.TravelComment;
import com.leo.service.ICommentService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName CommentServiceImpl
 * @Description TODO
 * @Author li.jiawei
 * @Date 2019/3/23 23:54
 * @Version 1.0
 */
@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private Sid sid;

    @Autowired
    private TravelCommentMapper commentMapper;

    @Autowired
    private TravelCommentCustomMapper commentCustomMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int insertComment(TravelComment comment) {
        comment.setId(sid.nextShort());
        return commentMapper.insertSelective(comment);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public TravelCommentCustom getCommentByUnion(String commentId) {
        return commentCustomMapper.getCommentByUnion(commentId);
    }
}
