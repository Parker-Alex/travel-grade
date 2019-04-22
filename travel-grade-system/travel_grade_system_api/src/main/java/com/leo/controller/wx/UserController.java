package com.leo.controller.wx;

import com.leo.annotation.LoginUser;
import com.leo.dto.TravelCommentCustom;
import com.leo.manager.TokenManager;
import com.leo.pojo.*;
import com.leo.service.*;
import com.leo.utils.JacksonUtil;
import com.leo.utils.MyResult;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author leo
 * @Description 用户相关操作API，所有操作都需要用户登录才能完成
 * @Date 15:34 2019/3/13
 */
@RestController
@RequestMapping("/wx/user")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private static final String PATHROOT = "E:/File/travel-grade";

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

    @Autowired
    private IProvinceService provinceService;

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

//        更新省份评分
        result = provinceService.updateProvinceByCityId(userCityRel.getCityId());
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
        List<TravelCity> recommends = recommendService.getUserRecommends(id);

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

    /**
     * @Author li.jiawei
     * @Description 上传推荐图片接口
     * @Date 15:14 2019/4/20
     */
    @PostMapping("/upload_image")
    public MyResult uploadImage(@RequestParam("file") MultipartFile file, @LoginUser String userId) throws IOException {
        LOGGER.info("------上传推荐图片方法开始------");
        LOGGER.info("请求参数file：" + file);

        System.out.println(userId);
        if (StringUtils.isEmpty(userId)) {
            return MyResult.errorMsg("用户没有登录");
        }

        String httpPath = saveFile(file, userId);
        if (StringUtils.isEmpty(httpPath)) {
            return MyResult.errorMsg("上传图片出错");
        }

        LOGGER.info("------上传推荐图片方法结束------");
        return MyResult.ok(httpPath);
    }

    /**
     * @Author li.jiawei
     * @Description 添加推荐城市接口
     * @Date 22:59 2019/4/20
     */
    @PostMapping(value = "/add_recommend", headers = "content-type=multipart/form-data")
    public MyResult addRecommend(String content, String provinceName, String cityName, @LoginUser String userId,
                                 @RequestParam("file") MultipartFile file) throws IOException {
        LOGGER.info("------添加推荐城市开始------");

        if (StringUtils.isEmpty(userId)) {
            return MyResult.errorMsg("用户没有登录");
        }

        TravelCity city = cityService.getCityByName(cityName);
        if (city == null) {
            return MyResult.errorMsg("暂时不能推荐该城市");
        }

        String httpPath = saveFile(file, userId);
        if (StringUtils.isEmpty(httpPath)) {
            return MyResult.errorMsg("上传图片错误");
        }

        int result = recommendService.addRecommend(userId, content, provinceName, cityName, httpPath);
        if (result == 0) {
            return MyResult.errorMsg("推荐城市失败");
        }

        LOGGER.info("------添加推荐城市结束------");
        return MyResult.ok();
    }

    /**
     * @Author li.jiawei
     * @Description 获得用户推荐城市列表接口
     * @Date 14:13 2019/4/21
     */
    @GetMapping("/recommend_cities")
    public MyResult getUserRecommendCities(@LoginUser String userId) {
        LOGGER.info("------获得用户推荐城市列表开始------");

        if (StringUtils.isEmpty(userId)) {
            return MyResult.errorMsg("用户没有登录");
        }

        List<TravelCity> recommends = recommendService.getUserRecommends(userId);

        LOGGER.info("------获得用户推荐城市列表开始------");
        return MyResult.ok(recommends);
    }

    /**
     * @Author li.jiawei
     * @Description 更新其他服务评分接口
     * @Date 1:25 2019/4/23
     */
    @PostMapping("/update_other")
    public MyResult updateOther(@RequestBody String body) {
        LOGGER.info("------更新其他服务评分开始------");
        LOGGER.info("请求参数body：" + body);
        LOGGER.info("------更新其他服务评分结束------");
        return MyResult.ok();
    }

    /**
     * @Author li.jiawei
     * @Description 保存上传的图片，并返回绝对路径
     * @Date 0:42 2019/4/21
     */
    private String saveFile(MultipartFile file, String userId) throws IOException {
        String fileName = UUID.randomUUID().toString() + ".jpg";

//        图片在数据库中的路径
        String dbPath = userId + "/" + fileName;
//        图片存放的绝对路径
        String realPath = PATHROOT + "/" + dbPath;

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            if (file != null) {
                File img = new File(realPath);
//            创建文件夹
                if (img.getParentFile() != null || !img.getParentFile().isDirectory()) {
                    img.getParentFile().mkdirs();
                }
                fileOutputStream = new FileOutputStream(img);
                inputStream = file.getInputStream();
//                进行文件拷贝
                IOUtils.copy(inputStream, fileOutputStream);
            }
        } catch (Exception e) {
            LOGGER.error("图片保存出错，错误原因：" + e);
            return null;
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        String httpPath = "http://localhost:8888/" + dbPath;
        return httpPath;
    }

}
