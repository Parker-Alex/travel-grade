package com.leo.controller.wx;

import com.github.pagehelper.PageInfo;
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
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

//    分页大小常数
    private static final Integer PAGE_SIZE = 4;

    @Autowired
    private ICommentService commentService;

    /**
     * @Author li.jiawei
     * @Description 用户发送评论接口
     * @Date 23:29 2019/3/23
     */
    @PostMapping("/send")
    public MyResult sendCommend(@LoginUser String userId,
                                @RequestBody String request) {
        LOGGER.info("------用户发送评论方法开始------");

        System.out.println(request);

        if (StringUtils.isEmpty(userId)) {
            return MyResult.errorMsg("需要登录");
        }

        String content = JacksonUtil.parseString(request, "content");
        if (StringUtils.isEmpty(content)) {
            return MyResult.errorMsg("评论为空");
        }

        String commentId = commentService.insertComment(request, userId);
        if (commentId == null) {
            return MyResult.errorMsg("------发送评论失败------");
        }

        TravelCommentCustom commentCustom = commentService.getCommentByUnion(commentId);
        if (commentCustom == null) {
            return MyResult.errorMsg("------获取评论信息失败------");
        }

        Map<String, Object> data = new HashMap<>();
        data.put("comment", commentCustom);

        LOGGER.info("------用户发送评论方法结束------");

        return MyResult.ok(data);
    }

    /**
     * @Author li.jiawei
     * @Description 获得某座城市所有评论接口
     * @Date 20:21 2019/3/25
     */
    @GetMapping("/all/{cityId}/{pageIndex}")
    public MyResult getAllComment(@PathVariable("cityId") String cityId,
                                  @PathVariable("pageIndex") Integer pageIndex,
                                  @LoginUser String userId) {

        LOGGER.info("------获得某座城市所有评论方法开始-------");

//        if (StringUtils.isEmpty(userId)) {
//            return MyResult.errorMsg("需要登录");
//        }

        if (StringUtils.isEmpty(cityId)) {
            return MyResult.errorMsg("城市标识为空");
        }

//        获得所有评论信息
        List<TravelCommentCustom> commentList = commentService.getAllComment(cityId, pageIndex, PAGE_SIZE);
        if (commentList == null) {
            return MyResult.errorMsg("获取评论失败");
        }

        Map<String, Object> data = new HashMap<>();
        data.put("list", commentList);
        data.put("userId", userId);

        LOGGER.info("------获得某座城市所有评论方法结束-------");

        return MyResult.ok(data);
    }

    /**
     * @Author li.jiawei
     * @Description 删除评论接口
     * @Date 21:22 2019/3/29
     */
    @GetMapping("/delete/{id}")
    public MyResult deleteComment(@PathVariable("id") String commentId, @LoginUser String userId) {

        LOGGER.info("------删除评论方法开始------");

        if (StringUtils.isEmpty(userId)) {
            return MyResult.errorMsg("没有登录");
        }

        if (StringUtils.isEmpty(commentId)) {
            return MyResult.errorMsg("评论标识为空");
        }

        int result = commentService.deleteComment(commentId, userId);

        if (result <= 0) {
            return MyResult.errorMsg("删除评论失败");
        }

        LOGGER.info("------删除评论方法开始------");

        return MyResult.ok();
    }

    /**
     * @Author li.jiawei
     * @Description 查看子评论接口
     * @Date 17:44 2019/4/9
     */
    @PostMapping("/more")
    public MyResult moreComment(@RequestBody String body, @LoginUser String userId) {

        LOGGER.info("------查看子评论方法开始------");
        LOGGER.info("请求参数：" + body);

        if (StringUtils.isEmpty(userId)) {
            return MyResult.errorMsg("没有登录");
        }

        List result = commentService.moreComment(body);
        System.out.println(result);

        LOGGER.info("------查看子评论方法结束------");
        return MyResult.ok(result);
    }
}
