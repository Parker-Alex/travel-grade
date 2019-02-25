package com.leo.service;

import com.leo.pojo.TravelProvince;

import java.util.List;

public interface IProvinceService {

//    通过评分获得热门省份
    List<TravelProvince> getHotProvinces();

//    分页获得省份
    List<TravelProvince> getProvinces(Integer pageNum, Integer pageSize);

    int addProvince(TravelProvince province);
}
