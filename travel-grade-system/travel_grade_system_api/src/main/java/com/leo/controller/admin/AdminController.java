package com.leo.controller.admin;

import com.leo.pojo.TravelUser;
import com.leo.service.IUserService;
import com.leo.utils.MyResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ClassName AdminController
 * @Description TODO
 * @Author li.jiawei
 * @Date 2019/4/24 23:44
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private IUserService userService;

    @GetMapping(value = "/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    @ResponseBody
    public MyResult doLogin(@RequestParam("username") String userName, @RequestParam("password") String password,
                            HttpSession session, HttpServletRequest request) {
        LOGGER.info("------后台登录方法开始-----");

        List<TravelUser> users = userService.getUsersByUsername(userName);
        TravelUser user = null;
        if (users.size() > 1) {
            LOGGER.error("数据中具有相同的用户名");
            return MyResult.errorMsg("系统内部错误");
        } else if (users.size() == 0) {
            return MyResult.errorMsg("用户名错误");
        } else {
            user = users.get(0);
        }

        if (!user.getPassword().equals(password)) {
            return MyResult.errorMsg("密码错误");
        }

        if (user.getGender() != 3) {
            return MyResult.errorMsg("该用户不是管理员身份");
        }

        session.setAttribute("user", user);

        LOGGER.info("------后台登录方法结束-----");
        return MyResult.ok();

    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
