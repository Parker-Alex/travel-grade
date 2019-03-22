package com.leo.controller.wx;

import com.leo.annotation.LoginUser;
import com.leo.dto.TravelCityCustom;
import com.leo.pojo.TravelCity;
import com.leo.pojo.TravelSearch;
import com.leo.pojo.TravelUserCityRel;
import com.leo.service.ICityService;
import com.leo.service.ISearchService;
import com.leo.service.IUserCityRelService;
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

@RestController
@RequestMapping("/wx/search")
public class SearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private ISearchService searchService;

    @Autowired
    private ICityService cityService;

    @Autowired
    private IUserCityRelService userCityRelService;

    /**
     * 得到热搜词列表和所有城市名称接口
     * @return
     */
    @GetMapping("/hot_key")
    public MyResult getHotKey() {

        LOGGER.info("调用得到热搜词列表和所有城市名称接口");

        List<String> hotkeys = searchService.getHotKeys();
        List<String> citiesName = cityService.getAllName();
        Map<String, Object> data = new HashMap<>();
        data.put("hotkeys", hotkeys);
        data.put("citiesName", citiesName);
        return MyResult.ok(data);
    }

    /**
     * 通过名字模糊查询城市接口
     * @param name
     * @return
     */
    @GetMapping("/search")
    public MyResult search(@LoginUser String userId,
                           @RequestParam("name") String name,
                           @RequestParam("id") String cityId) {

        LOGGER.info("调用通过名字模糊查询城市接口");

        Map<String, Object> data = new HashMap<>();

//        如果无用户登录，用户城市关系信息设为空，否则查询出相关记录
        if (StringUtils.isEmpty(userId)) {
            data.put("user_city_rel", null);
        } else {
            TravelUserCityRel userCityRel = userCityRelService.getRelByUserIdAndCityId(userId, cityId);
//            如果该用户还没对城市进行操作，用户城市关系同样设为空
            if (userCityRel == null) {
                data.put("user_city_rel", null);
            } else {
                data.put("user_city_rel", userCityRel);
            }
        }

        if (cityId.equals("null")) {
            cityId = null;
        }
        if (name.equals("null")) {
            name = null;
        }

//        判空
        if (StringUtils.isEmpty(cityId) && StringUtils.isEmpty(name)) {
            return MyResult.errorMsg("参数为空");
        }

//        查询得到城市
        TravelCityCustom city = cityService.getCity(cityId, name);

//        如果能找到匹配城市，才将该搜索词存入数据库
        if (city == null) {
            return MyResult.errorMsg("无法找到匹配城市");
        }
        data.put("city", city);

//        当通过名字查询时，才保存该搜索词
        if (!StringUtils.isEmpty(name)) {
            searchService.addHotKey(name);
        }

        return MyResult.ok(data);
    }
}
