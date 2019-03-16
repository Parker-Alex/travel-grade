package com.leo.mapper;

import com.leo.dto.TravelCityCustom;
import com.leo.pojo.TravelCity;
import com.leo.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TravelCityMapper extends MyMapper<TravelCity> {

//    得到所有城市的名字
    List<String> getAllName();
}