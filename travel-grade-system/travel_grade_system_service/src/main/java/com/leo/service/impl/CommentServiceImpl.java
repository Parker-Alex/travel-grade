package com.leo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leo.dto.TravelCommentCustom;
import com.leo.mapper.TravelCommentCustomMapper;
import com.leo.mapper.TravelCommentMapper;
import com.leo.pojo.TravelComment;
import com.leo.service.ICommentService;
import com.leo.utils.JacksonUtil;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    public String insertComment(String request, String userId) {
        String cityId = JacksonUtil.parseString(request, "cityId");
        String content = JacksonUtil.parseString(request, "content");
        String toUserId = JacksonUtil.parseString(request, "toUserId");
        String commentId = JacksonUtil.parseString(request, "id");

        TravelComment comment = new TravelComment();
        comment.setUserId(userId);
        comment.setCityId(cityId);
        comment.setContent(content);
        comment.setToUserId(toUserId);
        comment.setParentCommentId(commentId);
        comment.setId(sid.nextShort());
        int result = commentMapper.insertSelective(comment);
        if (result <= 0) {
            return null;
        }
        return comment.getId();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public TravelCommentCustom getCommentByUnion(String commentId) {
        return commentCustomMapper.getCommentByUnion(commentId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelCommentCustom> getAllComment(String cityId, Integer pageIndex, Integer pageSize) {
//        对起始位置进行
        if (pageIndex == null || pageIndex == 0) {
            pageIndex = 1;
        }
//        开始进行分页
        PageHelper.startPage(pageIndex, pageSize);
        List<TravelCommentCustom> commentList = commentCustomMapper.getAllComment(cityId);
        if (commentList == null || commentList.size() < 0) {
            return null;
        }
        for(TravelCommentCustom cc : commentList) {
            // 格式化日期
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cc.setDate(format.format(new Date()));
        }
        return commentList;
    }

    @Override
    public int deleteComment(String commentId, String userId) {
        Example example = new Example(TravelComment.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", commentId);
        criteria.andEqualTo("userId", userId);
        return commentMapper.deleteByExample(example);
    }
}
