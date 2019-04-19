package com.leo.controller.wx;

import com.leo.annotation.LoginUser;
import com.leo.dto.TravelCommentCustom;
import com.leo.pojo.*;
import com.leo.service.*;
import com.leo.utils.MyResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserRelService userRelService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private IRecommendService recommendService;

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

    /**
     * @Author li.jiawei
     * @Description 根据用户id获取用户信息接口
     * @Date 0:58 2019/4/18
     */
    @GetMapping("/get_other_user/{id}")
    public MyResult getOtherUser(@PathVariable("id") String id) {
        LOGGER.info("------根据用户id获取用户信息方法开始------");
        LOGGER.info("请求参数id：" + id);

        Map<String, Object> data = new HashMap<>();

        if (StringUtils.isEmpty(id)) {
            return MyResult.errorMsg("无法匹配用户");
        }

//        获得用户信息
        TravelUser user = userService.getUserByUserId(id);
        if (user == null) {
            return MyResult.errorMsg("无法匹配用户");
        }

//        获得用户粉丝信息
        List<TravelUserRel> fans = userRelService.getFans(id);

//        获得用户评论信息
        List<TravelCommentCustom> comments = commentService.getUserComments(id);

//        获得用户推荐信息
        List<TravelRecommend> recommends = recommendService.getUserRecommends(id);

//        获得用户去过城市信息
        List<TravelCity> gone_cities = cityService.userGoneCities(id);

//        获得用户想去城市信息
        List<TravelCity> like_cities = cityService.userLikeCities(id);

        data.put("user", user);
        data.put("fans", fans);
        data.put("comments", comments);
        data.put("recommends", recommends);
        data.put("gone_cities", gone_cities);
        data.put("like_cities", like_cities);

        LOGGER.info("------根据用户id获取用户信息方法结束------");
        return MyResult.ok(data);
    }
}
