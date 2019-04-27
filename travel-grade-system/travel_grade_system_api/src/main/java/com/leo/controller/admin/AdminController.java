package com.leo.controller.admin;

import com.leo.utils.MyResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping(value = "/login")
    public ModelAndView login() {
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    @ResponseBody
    public MyResult doLogin(@RequestParam("username") String userName, @RequestParam("password") String password) {
        System.out.println(userName);
        System.out.println(password);
        return MyResult.ok();

    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
