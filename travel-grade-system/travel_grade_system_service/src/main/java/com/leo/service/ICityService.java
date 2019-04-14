package com.leo.service;

import com.github.pagehelper.PageInfo;
import com.leo.dto.TravelCityCustom;
import com.leo.pojo.TravelCity;

import java.util.List;

public interface ICityService {

//    通过评分获得热门城市
    List<TravelCity> getHotCities();
    
    /**
     * @Author li.jiawei
     * @Description 通过评论人数获得城市列表
     * @Date 14:08 2019/4/13
     */
    List<TravelCity> getCitiesByCommendCount();

    /**
     * @Author li.jiawei
     * @Description 通过点赞数获得城市列表
     * @Date 14:09 2019/4/13
     */
    List<TravelCity> getCitiesByFavourCount();

    /**
     * @Author li.jiawei
     * @Description 通过想去人数获得城市列表
     * @Date 14:10 2019/4/13
     */
    List<TravelCity> getCitiesByLikeCount();

    /**
     * @Author li.jiawei
     * @Description 通过去过人数获得城市列表
     * @Date 14:10 2019/4/13
     */
    List<TravelCity> getCitiesByGoneCount();

    /**
     * @Author li.jiawei
     * @Description 通过评分人数获得城市列表
     * @Date 14:27 2019/4/13
     */
    List<TravelCity> getCitiesByGradeCount();

    /**
     * @Author li.jiawei
     * @Description 根据不同的条件分页获得城市列表，index代表查询的条件，0表示按评分查询
     * @Date 1:32 2019/4/14
     */
    PageInfo<TravelCity> getCityList(Integer index, Integer pageNum);

    List<TravelCity> getCities(Integer pageNum, Integer pageSize);

    int addCity(TravelCity city);

    List<TravelCity> getCitiesByProvinceId(String provinceId);

    TravelCityCustom getCity(String cityId, String name);

    List<String> getAllName();

    int updateCity(String cityId);
}
