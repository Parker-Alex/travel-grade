package com.leo.service;

import com.github.pagehelper.PageInfo;
import com.leo.pojo.TravelProvince;

import java.util.List;

public interface IProvinceService {

//    通过评分获得热门省份
    List<TravelProvince> getHotProvinces();

//    分页获得省份列表
    PageInfo<TravelProvince> getProvinces(Integer pageNum, Integer pageSize);

    /**
     * @Author li.jiawei
     * @Description 后台获取省份列表
     * @Date 1:13 2019/4/30
     */
    PageInfo<TravelProvince> getProvincesByAdmin(Integer pageNum, Integer pageSize);

    int addProvince(TravelProvince province);

//    得到说有省份名字
    List<String> getAllName();

    TravelProvince getProvince(String body);

    int updateProvinceByCityId(String cityId);

    Double getAvgGrade(String provinceId);

    TravelProvince getProvinceById(String id);

    /**
     * @Author li.jiawei
     * @Description 后台添加省份方法
     * @Date 23:51 2019/5/1
     */
    int addProvinceByAdmin(TravelProvince province);
    
    /**
     * @Author li.jiawei
     * @Description 后台修改省份方法
     * @Date 0:30 2019/5/2
     */
    int updateProvinceByAdmin(TravelProvince newProvince);

    int deleteProvinceById(String provinceId);
}
