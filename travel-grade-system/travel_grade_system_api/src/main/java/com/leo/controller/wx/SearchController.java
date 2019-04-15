package com.leo.controller.wx;

import com.leo.annotation.LoginUser;
import com.leo.dto.TravelCityCustom;
import com.leo.pojo.TravelCity;
import com.leo.pojo.TravelProvince;
import com.leo.pojo.TravelSearch;
import com.leo.pojo.TravelUserCityRel;
import com.leo.service.ICityService;
import com.leo.service.IProvinceService;
import com.leo.service.ISearchService;
import com.leo.service.IUserCityRelService;
import com.leo.utils.JacksonUtil;
import com.leo.utils.MyResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @Autowired
    private IProvinceService provinceService;

    /**
     * 得到热搜词列表和所有城市名称接口
     * @return
     */
    @GetMapping("/hot_key")
    public MyResult getHotKey() {

        LOGGER.info("------调用得到热搜词列表和所有城市名称方法开始------");

        List<String> hotkeys = searchService.getHotKeys();
        List<String> citiesName = cityService.getAllName();
        List<String> provincesName = provinceService.getAllName();

//        匹配关键字列表，包含所有城市和省份名称
        List<String> matchList = new ArrayList<>();
        matchList.addAll(citiesName);
        matchList.addAll(provincesName);

        Map<String, Object> data = new HashMap<>();
        data.put("hotkeys", hotkeys);
        data.put("matchList", matchList);
        data.put("provincesName", provincesName);

        LOGGER.info("------调用得到热搜词列表和所有城市名称方法结束------");
        return MyResult.ok(data);
    }

    /**
     * 查询城市或省份接口
     * @param name
     * @return
     */
    @GetMapping("/city")
    public MyResult searchCity(@LoginUser String userId,
                           @RequestParam("name") String name,
                           @RequestParam("id") String cityId) {

        LOGGER.info("------查询城市或省份方法开始------");

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

        LOGGER.info("------查询城市方法或省份结束------");

        return MyResult.ok(data);
    }

    /**
     * @Author li.jiawei
     * @Description 查询单个省份对象接口
     * @Date 15:04 2019/4/15
     */
    @PostMapping("/province")
    public MyResult searchProvince(@RequestBody String body) {
        LOGGER.info("------查询单个省份对象方法开始-----");
        LOGGER.info("请求参数body：" + body);

        Map<String, Object> data = new HashMap<>();

        TravelProvince province = provinceService.getProvince(body);

        if (province == null) {
            return MyResult.errorMsg("没有找到匹配省份");
        }

//        添加搜索词
        String name = JacksonUtil.parseString(body, "name");
        if (!StringUtils.isEmpty(name)) {
            searchService.addHotKey(name);
        }

//        得到该省份下的城市列表
        List<TravelCity> cities = cityService.getCitiesByProvinceId(province.getId());
        if (cities != null) {
            data.put("cities", cities);
        }
        data.put("province", province);

        LOGGER.info("------查询单个省份对象方法结束-----");
        return MyResult.ok(data);
    }

}
