package com.leo.controller.wx;

import com.github.pagehelper.PageInfo;
import com.leo.annotation.LoginUser;
import com.leo.pojo.TravelCity;
import com.leo.service.ICityService;
import com.leo.utils.MyResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName CityController
 * @Description 城市相关操作API
 * @Author li.jiawei
 * @Date 2019/4/2 17:34
 * @Version 1.0
 */
@RestController
@RequestMapping("/wx/city")
public class CityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private ICityService cityService;

    /**
     * @Author li.jiawei
     * @Description 获得分类城市列表接口
     * @Date 1:27 2019/4/14
     */
    @GetMapping("/city_list/{index}/{pageNum}")
    public MyResult getCityList(@PathVariable("index") Integer index, @PathVariable("pageNum") Integer pageNum) {

        LOGGER.info("------调用获得分类城市列表方法开始------");

        if (index == null) {
            return MyResult.errorMsg("城市分类查询错误");
        }

        PageInfo<TravelCity> cities = cityService.getCityList(index, pageNum);

        LOGGER.info("------调用获得分类城市列表方法结束------");
        return MyResult.ok(cities);
    }
}
