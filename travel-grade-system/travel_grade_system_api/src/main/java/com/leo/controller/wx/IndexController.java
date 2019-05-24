package com.leo.controller.wx;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leo.enums.TravelOtherEnum;
import com.leo.pojo.TravelCity;
import com.leo.pojo.TravelProvince;
import com.leo.service.ICityService;
import com.leo.service.IProvinceService;
import com.leo.utils.MyResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/index")
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    private static final int PAGE_SIZE = 5;

    @Autowired
    private ICityService cityService;

    @Autowired
    private IProvinceService provinceService;

    /**
     * 获取热门城市和省份列表接口
     * @return
     */
    @GetMapping("/index_data")
    public MyResult getCitiesAndProvinces() {

        LOGGER.info("------调用获取城市和省份列表方法开始------");

//        防止用户对城市进行相关操作后，首页无法读取最新的城市信息
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        获得热门城市列表
        List<TravelCity> hotCities = cityService.getHotCities();
        List<TravelCity> commendCities = cityService.getCitiesByCommendCount();
        List<TravelCity> favourCities = cityService.getCitiesByFavourCount();
        List<TravelCity> goneCities = cityService.getCitiesByGoneCount();
        List<TravelCity> gradeCities = cityService.getCitiesByGradeCount();
        List<TravelCity> likeCities = cityService.getCitiesByLikeCount();
//        获取省份列表
        List<TravelProvince> provinces = provinceService.getHotProvinces();
//        获得其他评分项
        List<String> types = TravelOtherEnum.getAllType();

        Map<String, Object> data = new HashMap<>();
        data.put("hotCities", hotCities);
        data.put("commendCities", commendCities);
        data.put("favourCities", favourCities);
        data.put("goneCities", goneCities);
        data.put("gradeCities", gradeCities);
        data.put("likeCities", likeCities);
        data.put("provinces", provinces);
        data.put("types", types);

        LOGGER.info("------调用获取城市和省份列表方法结束------");

        return MyResult.ok(data);
    }

    /**
     * 获得所有省份接口
     */
    @GetMapping("/get_provinces/{pageNum}")
    public MyResult getAllProvinces(@PathVariable("pageNum") Integer pageNum) {

        LOGGER.info("------调用获得所有省份方法开始------");
        LOGGER.info("参数pageNum：" + pageNum);

//        初始化分页起始值
        if (StringUtils.isEmpty(pageNum) || pageNum == 0) {
            pageNum = 1;
        }

        PageInfo<TravelProvince> provinces = provinceService.getProvinces(pageNum, 0);

        LOGGER.info("------调用获得所有省份方法结束------");

        return MyResult.ok(provinces);
    }

    /**
     * 通过省份id获取所属城市列表接口
     * @param provinceId
     * @return
     */
    @GetMapping("/get_cities/{provinceId}")
    public MyResult getCities(@PathVariable("provinceId") String provinceId) {

        LOGGER.info("------调用通过省份id获取所属城市列表方法开始------");
        LOGGER.info("参数provinceId：" + provinceId);

        if (StringUtils.isEmpty(provinceId)) {
            return MyResult.errorMsg("参数为空");
        }

        List<TravelCity> cities = cityService.getCitiesByProvinceId(provinceId);

        LOGGER.info("------调用通过省份id获取所属城市列表方法结束------");

        return MyResult.ok(cities);

    }

    /**
     * 添加省份接口
     */
    @GetMapping("/add_province")
    public MyResult addProvince(@RequestParam("name") String name,
                                @RequestParam("cover") String cover,
                                @RequestParam("introduce") String introduce,
                                @RequestParam("reason") String reason) {

        LOGGER.info("------添加省份方法开始------");

        TravelProvince province = new TravelProvince();
        province.setName(name);
        province.setCover(cover);
        province.setIntroduce(introduce);
        province.setReason(reason);

        int result = provinceService.addProvince(province);
        if (result == 0) {
            return MyResult.errorMsg("插入失败");
        }

        LOGGER.info("------添加省份方法结束------");

        return MyResult.ok();
    }

    /**
     * 添加城市接口
     */
    @GetMapping("/add_city")
    public MyResult addCity(@RequestParam("name") String name,
                            @RequestParam("cover") String cover,
                            @RequestParam("introduce") String introduce,
                            @RequestParam("provinceId") String provinceId) {

        LOGGER.info("------添加省份方法开始------");

        TravelCity city= new TravelCity();
        city.setName(name);
        city.setProvinceId(provinceId);
        city.setCover(cover);
        city.setIntroduce(introduce);

        int result = cityService.addCity(city);
        if (result == 0) {
            return MyResult.errorMsg("插入失败");
        }

        LOGGER.info("------添加省份方法结束------");
        return MyResult.ok();
    }
}
