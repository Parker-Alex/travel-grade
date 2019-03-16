package com.leo.mapper;

import com.leo.dto.TravelCityCustom;
import com.leo.pojo.TravelCity;
import com.leo.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TravelCityCustomMapper extends MyMapper<TravelCityCustom> {

    List<TravelCityCustom> getCity(@Param("cityId") String cityId, @Param("cityName") String cityName);
}