package com.leo.controller.admin;

import com.github.pagehelper.PageInfo;
import com.leo.dto.TravelCommentCustom;
import com.leo.service.ICommentService;
import com.leo.utils.MyResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName AdminCommentController
 * @Description TODO
 * @Author li.jiawei
 * @Date 2019/5/2 1:04
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin/comment")
public class AdminCommentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminCommentController.class);

    @Autowired
    private ICommentService commentService;

    /**
     * @Author li.jiawei
     * @Description 跳转评论管理首页
     * @Date 1:06 2019/5/2
     */
    @GetMapping("")
    public String index(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "10") int limit, HttpServletRequest request) {
        LOGGER.info("------跳转评论管理首页方法开始------");
        PageInfo<TravelCommentCustom> comments = commentService.getAllCommentsByAdmin(page, limit);
        request.setAttribute("comments", comments);
        LOGGER.info("------跳转评论管理首页方法结束------");
        return "comments";
    }

    /**
     * @Author li.jiawei
     * @Description 删除评论方法
     * @Date 1:56 2019/5/2
     */
    @PostMapping("/delete")
    @ResponseBody
    public MyResult commentDelete(@RequestParam("cid") String commentId) {
        LOGGER.info("------删除评论方法开始------");

        int result = commentService.deleteCommentById(commentId);
        if (result <= 0) {
            return MyResult.errorMsg("删除评论失败");
        }

        LOGGER.info("------删除评论方法结束------");
        return MyResult.ok();
    }
}
