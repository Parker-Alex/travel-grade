package com.leo.controller.admin;

import com.github.pagehelper.PageInfo;
import com.leo.dto.TravelCityCustom;
import com.leo.pojo.TravelCity;
import com.leo.pojo.TravelProvince;
import com.leo.service.ICityService;
import com.leo.service.IProvinceService;
import com.leo.utils.JacksonUtil;
import com.leo.utils.MyResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName CityController
 * @Description TODO
 * @Author li.jiawei
 * @Date 2019/4/28 1:04
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin/city")
public class AdminCityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminCityController.class);

    @Autowired
    private ICityService cityService;

    @Autowired
    private IProvinceService provinceService;

    /**
     * @Author li.jiawei
     * @Description 刚开始进入城市列表接口
     * @Date 0:45 2019/4/30
     */
    @GetMapping("")
    public String index(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "10") int limit, HttpServletRequest request) {
        LOGGER.info("------调用后台获得城市列表方法开始-----");
        List<TravelCity> list = cityService.getCities(page, limit);
        PageInfo<TravelCity> cities = new PageInfo<>(list);
        request.setAttribute("cities", cities);
        LOGGER.info("------调用后台获得城市列表方法结束-----");
        return "cities";
    }

    /**
     * @Author li.jiawei
     * @Description 后台进入城市信息详情页面接口
     * @Date 1:17 2019/4/30
     */
    @GetMapping("/publish")
    public String cityPublic(HttpServletRequest request) {
        LOGGER.info("------进入后台城市修改页面方法开始-----");
        PageInfo<TravelProvince> pageInfo = provinceService.getProvinces(1, 0);
        List<TravelProvince> provinces = pageInfo.getList();
        System.out.println(provinces);
        request.setAttribute("provinces", provinces);
        LOGGER.info("------进入后台城市修改页面方法结束-----");
        return "city_edit";
    }

    /**
     * @Author li.jiawei
     * @Description 后台添加城市接口
     * @Date 1:17 2019/4/30
     */
    @PostMapping("/publish")
    @ResponseBody
    public MyResult cityPublish(TravelCity city) {
        LOGGER.info("------调用后台城市添加方法开始-----");
        if (city.getProvinceId() == null || city.getProvinceId().equals("")) {
            return MyResult.errorMsg("请输入城市所属省份");
        }

        int result = cityService.addCity(city);
        if (result <= 0) {
            return MyResult.errorMsg("添加城市失败");
        }
        LOGGER.info("------调用后台城市添加方法结束-----");
        return MyResult.ok();
    }

    /**
     * @Author li.jiawei
     * @Description 后台修改城市接口
     * @Date 1:18 2019/4/30
     */
    @PostMapping("/modify")
    @ResponseBody
    public MyResult cityModify(TravelCity newCity) {
        LOGGER.info("------调用后台城市修改方法开始-----");

        int result = cityService.updateCityByAdmin(newCity);
        if (result == 0) {
            return MyResult.errorMsg("修改城市失败");
        }

        LOGGER.info("------调用后台城市修改方法结束-----");
        return MyResult.ok();
    }

    /**
     * @Author li.jiawei
     * @Description 后台查询城市接口
     * @Date 1:19 2019/4/30
     */
    @GetMapping("/{cityId}")
    public String cityFind(@PathVariable("cityId") String cityId, HttpServletRequest request) {
        LOGGER.info("------调用后台查询城市方法开始-----");

        TravelCity city = cityService.getCityByCityId(cityId);
        TravelProvince province = provinceService.getProvinceById(city.getProvinceId());

        request.setAttribute("city", city);
        request.setAttribute("pid", province.getId());
        request.setAttribute("pName", province.getName());

        LOGGER.info("------调用后台查询城市方法结束-----");
        return "city_edit";
    }

    /**
     * @Author li.jiawei
     * @Description 后台删除城市方法
     * @Date 16:25 2019/5/1
     */
    @PostMapping("/delete")
    @ResponseBody
    public MyResult cityDelete(@RequestParam("cid") String cityId) {
        LOGGER.info("------调用后台删除城市方法开始-----");

        int result = cityService.deleteCityByCityId(cityId);

        if (result <= 0) {
            return MyResult.errorMsg("删除城市失败");
        }

        LOGGER.info("------调用后台删除城市方法结束-----");
        return MyResult.ok();
    }
}
