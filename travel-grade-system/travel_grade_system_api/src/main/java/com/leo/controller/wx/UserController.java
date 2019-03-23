package com.leo.controller.wx;

import com.leo.annotation.LoginUser;
import com.leo.pojo.TravelUserCityRel;
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

    /**
     * @Author li.jiawei
     * @Description 更新用户与城市之间关系接口
     * @Date 20:03 2019/3/22
     */
    @PostMapping("/update_user_city_rel")
    public MyResult updateUserCityRel(@LoginUser String userId,
                                      @RequestBody TravelUserCityRel userCityRel) {
        LOGGER.info("------更新用户与城市之间关系方法开始------");

        if (StringUtils.isEmpty(userId)) {
            return MyResult.errorMsg("没有登录");
        }

        TravelUserCityRel userCityRelNew = userCityRelService.getRelByUserIdAndCityId(userId, userCityRel.getCityId());
        int result = 0;
//        用户第一次与城市建立关系，则新建关系
        if (userCityRelNew == null) {
            userCityRel.setUserId(userId);
            result = userCityRelService.insertRel(userCityRel);
        } else {
//            用户不是第一次建立关系，则在原来关系的基础上修改
            userCityRelNew.setIsFavour(userCityRel.getIsFavour());
            userCityRelNew.setIsGone(userCityRel.getIsGone());
            userCityRelNew.setIsLike(userCityRel.getIsLike());
            result = userCityRelService.updateRel(userCityRelNew);
        }

        if (result <= 0) {
            return MyResult.errorMsg("操作失败");
        }
        return MyResult.ok();
    }
}
