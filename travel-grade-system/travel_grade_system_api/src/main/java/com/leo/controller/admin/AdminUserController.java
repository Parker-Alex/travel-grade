package com.leo.controller.admin;

import com.github.pagehelper.PageInfo;
import com.leo.pojo.TravelUser;
import com.leo.service.IUserService;
import com.leo.utils.MyResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName AdminUserComtroller
 * @Description TODO
 * @Author li.jiawei
 * @Date 2019/5/2 2:11
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin/users")
public class AdminUserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminUserController.class);

    @Autowired
    private IUserService userService;

    /**
     * @Author li.jiawei
     * @Description 后台跳转到用户管理页面方法
     * @Date 2:16 2019/5/2
     */
    @GetMapping("")
    public String index(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "10") int limit, HttpServletRequest request) {
        LOGGER.info("------后台跳转到用户管理页面方法开始------");

        PageInfo<TravelUser> users = userService.getAllUsersByAdmin(page, limit);
        request.setAttribute("users", users);

        LOGGER.info("------后台跳转到用户管理页面方法开始------");
        return "users";
    }
    
    /**
     * @Author li.jiawei
     * @Description 后台停用用户方法
     * @Date 2:45 2019/5/2
     */
    @PostMapping("/stop")
    @ResponseBody
    public MyResult userStop(@RequestParam("uid") String userId) {
        LOGGER.info("------后台停用用户方法开始------");

        int result = userService.stopUser(userId);
        if (result <= 0) {
            return MyResult.errorMsg("停用用户失败");
        }

        LOGGER.info("------后台停用用户方法结束------");
        return MyResult.ok();
    }

    /**
     * @Author li.jiawei
     * @Description 后台恢复用户方法
     * @Date 2:58 2019/5/2
     */
    @PostMapping("/resume")
    @ResponseBody
    public MyResult userResume(@RequestParam("uid") String userId) {
        LOGGER.info("------后台恢复用户方法开始------");

        int result = userService.resumeUser(userId);
        if (result <= 0) {
            return MyResult.errorMsg("恢复用户失败");
        }

        LOGGER.info("------后台恢复用户方法结束------");
        return MyResult.ok();
    }
}
