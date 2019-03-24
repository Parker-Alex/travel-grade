package com.leo.controller.wx;

import com.leo.annotation.LoginUser;
import com.leo.dto.TravelCommentCustom;
import com.leo.pojo.TravelComment;
import com.leo.service.ICommentService;
import com.leo.utils.JacksonUtil;
import com.leo.utils.MyResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName CommentController
 * @Description 用户评论相关接口
 * @Author li.jiawei
 * @Date 2019/3/23 23:26
 * @Version 1.0
 */
@RestController
@RequestMapping("/wx/comment")
public class CommentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private ICommentService commentService;

    /**
     * @Author li.jiawei
     * @Description 接收用户发送评论接口
     * @Date 23:29 2019/3/23
     */
    @PostMapping("/send")
    public MyResult sendCommend(@LoginUser String userId,
                                @RequestBody String request) {
        LOGGER.info("------接收用户发送评论方法开始------");

        if (StringUtils.isEmpty(userId)) {
            return MyResult.errorMsg("请登录");
        }

        String cityId = JacksonUtil.parseString(request, "cityId");
        String content = JacksonUtil.parseString(request, "content");
        if (StringUtils.isEmpty(content)) {
            return MyResult.errorMsg("评论为空");
        }

        TravelComment comment = new TravelComment();
        comment.setUserId(userId);
        comment.setCityId(cityId);
        comment.setContent(content);

        int result = commentService.insertComment(comment);
        if (result <= 0) {
            return MyResult.errorMsg("------发送评论失败------");
        }

        TravelCommentCustom commentCustom = commentService.getCommentByUnion(comment.getId());
        if (commentCustom == null) {
            return MyResult.errorMsg("------获取评论信息失败------");
        }

        Map<String, Object> data = new HashMap<>();
        data.put("comment", commentCustom);
        data.put("userId", userId);

        LOGGER.info("------接收用户发送评论方法结束------");

        return MyResult.ok(data);
    }
}
