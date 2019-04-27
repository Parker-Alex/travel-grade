package com.leo.controller.admin;

import com.github.pagehelper.PageInfo;
import com.leo.pojo.TravelCity;
import com.leo.service.ICityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("")
    public String index(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "15") int limit, HttpServletRequest request) {
        LOGGER.info("------调用后台获得城市列表方法开始-----");
        List<TravelCity> list = cityService.getCities(page, limit);
        PageInfo<TravelCity> cities = new PageInfo<>(list);
        System.out.println(cities);
        System.out.println(cities.getList());
        request.setAttribute("cities", cities);
        LOGGER.info("------调用后台获得城市列表方法结束-----");
        return "cities";
    }
}
