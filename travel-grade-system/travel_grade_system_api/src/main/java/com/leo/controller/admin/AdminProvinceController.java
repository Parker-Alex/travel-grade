package com.leo.controller.admin;

import com.github.pagehelper.PageInfo;
import com.leo.pojo.TravelCity;
import com.leo.pojo.TravelProvince;
import com.leo.service.IProvinceService;
import com.leo.utils.MyResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName AdminProvinceController
 * @Description TODO
 * @Author li.jiawei
 * @Date 2019/4/30 0:43
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin/province")
public class AdminProvinceController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminProvinceController.class);

    @Autowired
    private IProvinceService provinceService;

    /**
     * @Author li.jiawei
     * @Description 刚开始进入省份列表接口
     * @Date 0:46 2019/4/30
     */
    @GetMapping("")
    public String index(@RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "10") int limit, HttpServletRequest request) {
        LOGGER.info("------调用后台获得省份列表方法开始-----");
        PageInfo<TravelProvince> provinces = provinceService.getProvincesByAdmin(page, limit);
        request.setAttribute("provinces", provinces);
        LOGGER.info("------调用后台获得省份列表方法结束-----");
        return "provinces";
    }

    /**
     * @Author li.jiawei
     * @Description 进入省份发布页面方法
     * @Date 17:27 2019/5/1
     */
    @GetMapping("/publish")
    public String provincePublish() {
        return "province_edit";
    }

    /**
     * @Author li.jiawei
     * @Description 后台添加省份方法
     * @Date 23:43 2019/5/1
     */
    @PostMapping("/publish")
    @ResponseBody
    public MyResult provincePublish(TravelProvince province) {
        LOGGER.info("------调用后台添加省份方法开始-----");

        int result = provinceService.addProvinceByAdmin(province);

        if (result <= 0) {
            return MyResult.errorMsg("添加省份失败");
        }

        LOGGER.info("------调用后台添加省份方法结束-----");
        return MyResult.ok();
    }

    /**
     * @Author li.jiawei
     * @Description 查看省份对象方法
     * @Date 0:16 2019/5/2
     */
    @GetMapping("/{pid}")
    public String provinceFind(@PathVariable("pid") String provinceId, HttpServletRequest request) {
        LOGGER.info("------调用后台查询省份方法开始-----");
        TravelProvince province = provinceService.getProvinceById(provinceId);
        if (province == null) {
            return "comm/error_404";
        }

        request.setAttribute("province", province);
        LOGGER.info("------调用后台查询省份方法开始-----");
        return "province_edit";
    }

    /**
     * @Author li.jiawei
     * @Description 修改省份信息方法
     * @Date 0:23 2019/5/2
     */
    @PostMapping("/modify")
    @ResponseBody
    public MyResult provinceModify(TravelProvince province) {
        LOGGER.info("------调用后台修改省份方法开始-----");

        int result = provinceService.updateProvinceByAdmin(province);
        if (result <= 0) {
            return MyResult.errorMsg("修改省份失败");
        }
        LOGGER.info("------调用后台修改省份方法结束-----");
        return MyResult.ok();
    }

    /**
     * @Author li.jiawei
     * @Description 删除省份方法
     * @Date 0:40 2019/5/2
     */
    @PostMapping("/delete")
    @ResponseBody
    public MyResult provinceDelete(@RequestParam("pid") String provineId) {
        LOGGER.info("------调用后台删除省份方法开始-----");

        int result = provinceService.deleteProvinceById(provineId);
        if (result <= 0) {
            return MyResult.errorMsg("删除省份失败");
        }

        LOGGER.info("------调用后台删除省份方法结束-----");
        return MyResult.ok();
    }
}
