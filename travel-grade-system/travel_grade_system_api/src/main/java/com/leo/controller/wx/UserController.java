package com.leo.controller.wx;

import com.leo.annotation.LoginUser;
import com.leo.pojo.TravelUserCityRel;
import com.leo.service.ICityService;
import com.leo.service.IUserCityRelService;
import com.leo.utils.MyResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @Author leo
 * @Description 用户相关操作API，所有操作都需要用户登录才能完成
 * @Date 15:34 2019/3/13
 */
@RestController
@RequestMapping("/wx/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserCityRelService userCityRelService;

    @Autowired
    private ICityService cityService;

    /**
     * @Author li.jiawei
     * @Description 更新用户与城市之间关系接口
     * @Date 20:03 2019/3/22
     */
    @PostMapping("/update_user_city_rel")
    public MyResult updateUserCityRel(@LoginUser String userId,
                                      @RequestBody TravelUserCityRel userCityRel) {
        LOGGER.info("------更新用户与城市之间关系方法开始------");
        LOGGER.info("参数userCityRel：" + userCityRel);

        if (StringUtils.isEmpty(userId)) {
            return MyResult.errorMsg("没有登录");
        }

        TravelUserCityRel userCityRelNew = userCityRelService.getRelByUserIdAndCityId(userId, userCityRel.getCityId());

        int result = 0;

        result = userCityRelService.judgeRel(userCityRelNew, userCityRel, userId);
        if (result <= 0) {
            return MyResult.errorMsg("操作失败");
        }

        result = cityService.updateCity(userCityRel.getCityId());
        if (result <= 0) {
            return MyResult.errorMsg("操作失败");
        }

        LOGGER.info("------更新用户与城市之间关系方法结束------");

        return MyResult.ok();
    }
}
