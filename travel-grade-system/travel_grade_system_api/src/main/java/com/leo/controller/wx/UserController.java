package com.leo.controller.wx;

import com.leo.utils.MyResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author leo
 * @Description 用户相关操作API
 * @Date 15:34 2019/3/13
 */
@RestController
@RequestMapping("/wx/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /**
     * @Author leo
     * @Description 点赞接口
     * @Date 15:37 2019/3/13
     */
    public MyResult giveALike() {
        return null;
    }
}
