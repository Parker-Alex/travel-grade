package com.leo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leo.dto.TravelCommentCustom;
import com.leo.dto.TravelUserRelCustom;
import com.leo.mapper.TravelCommentCustomMapper;
import com.leo.mapper.TravelCommentMapper;
import com.leo.mapper.TravelUserMapper;
import com.leo.pojo.TravelComment;
import com.leo.pojo.TravelUser;
import com.leo.service.ICommentService;
import com.leo.utils.JacksonUtil;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
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

    @Autowired
    private TravelUserMapper userMapper;

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
        if (commentId != null || !commentId.equals("")) {
            System.out.println("1111");
            comment.setParentCommentId(commentId);
        }
        comment.setId(sid.nextShort());
        comment.setSendDate(new Date());

        int result = commentMapper.insertSelective(comment);
        if (result <= 0) {
//            return null;
            throw new RuntimeException("添加评论失败");
        }

//        根据用户评论数更新用户称号
        List<TravelCommentCustom> list = getUserComments(userId);
        if (list.size() > 100) {
            TravelUser user = userMapper.selectByPrimaryKey(userId);
            user.setTitleName("评论达人");
            result = userMapper.updateByPrimaryKey(user);
            if (result <= 0) {
                throw new RuntimeException("更新用户评论称号失败");
            }
        }
        return comment.getId();
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public TravelCommentCustom getCommentByUnion(String commentId) {
        TravelCommentCustom commentCustom = commentCustomMapper.getCommentByUnion(commentId);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        commentCustom.setDate(format.format(commentCustom.getSendDate()));
        return commentCustom;
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
            cc.setDate(format.format(cc.getSendDate()));
        }
        return commentList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int deleteComment(String commentId, String userId) {
        Example example = new Example(TravelComment.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", commentId);
        criteria.andEqualTo("userId", userId);
        return commentMapper.deleteByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelCommentCustom> moreComment(String body) {
        String cityId = JacksonUtil.parseString(body, "cityId");
        String commentId = JacksonUtil.parseString(body, "commentId");

        if (StringUtils.isEmpty(cityId) || StringUtils.isEmpty(commentId)) {
            return null;
        }

        List<TravelCommentCustom> comments = commentCustomMapper.moreComment(commentId, cityId);
        for(TravelCommentCustom cc : comments) {
            // 格式化日期
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cc.setDate(format.format(cc.getSendDate()));
        }
        return comments;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelCommentCustom> getUserComments(String userId) {
        List<TravelCommentCustom> commentList = commentCustomMapper.getUserComments(userId);
        for(TravelCommentCustom cc : commentList) {
            // 格式化日期
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cc.setDate(format.format(cc.getSendDate()));
        }
        return commentList;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PageInfo<TravelCommentCustom> getAllCommentsByAdmin(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TravelCommentCustom> list = commentCustomMapper.getAllCommentsByAdmin();
        for(TravelCommentCustom cc : list) {
            // 格式化日期
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cc.setDate(format.format(cc.getSendDate()));
        }
        return new PageInfo<>(list);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int deleteCommentById(String commentId) {
        int result = commentMapper.deleteByPrimaryKey(commentId);
        if (result <= 0) {
            throw new RuntimeException("后台删除评论失败");
        }
        return result;
    }
}
