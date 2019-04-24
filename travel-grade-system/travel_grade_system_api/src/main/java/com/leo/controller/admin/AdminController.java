package com.leo.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

//    @RequestMapping("/test")
//    public ModelAndView test() {
//        return new ModelAndView("index", "hi", "hello");
//    }

    @RequestMapping("/test")
    public String test() {
        System.out.println("111");
        return "index";
    }
}
