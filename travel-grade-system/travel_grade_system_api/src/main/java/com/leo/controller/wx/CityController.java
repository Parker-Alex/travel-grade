package com.leo.controller.wx;

import com.leo.annotation.LoginUser;
import com.leo.utils.MyResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName CityController
 * @Description 城市相关操作API
 * @Author li.jiawei
 * @Date 2019/4/2 17:34
 * @Version 1.0
 */
@RestController
@RequestMapping("/city")
public class CityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    /**
     * @Author li.jiawei
     * @Description 更新城市信息接口
     * @Date 17:36 2019/4/2
     */
    @GetMapping("/update")
    public MyResult updateCity(@LoginUser String userId) {
        LOGGER.info("------更新城市信息方法开始------");

//        如果无用户登录，不进行更新
        if (StringUtils.isEmpty(userId)) {
            return MyResult.ok();
        }
        return null;
    }
}
