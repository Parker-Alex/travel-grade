package com.leo.service;

import com.github.pagehelper.PageInfo;
import com.leo.pojo.TravelProvince;

import java.util.List;

public interface IProvinceService {

//    通过评分获得热门省份
    List<TravelProvince> getHotProvinces();

//    分页获得省份列表
    PageInfo<TravelProvince> getProvinces(Integer pageNum, Integer pageSize);

    int addProvince(TravelProvince province);

//    得到说有省份名字
    List<String> getAllName();

    TravelProvince getProvince(String body);
}
