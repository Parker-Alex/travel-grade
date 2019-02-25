package com.leo.service;

import com.leo.pojo.TravelCity;

import java.util.List;

public interface ICityService {

//    通过评分获得热门城市
    List<TravelCity> getHotCities();

    List<TravelCity> getCities(Integer pageNum, Integer pageSize);

    int addCity(TravelCity city);

    List<TravelCity> getCitiesByProvinceId(String provinceId);
}
