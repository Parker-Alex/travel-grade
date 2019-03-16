package com.leo.mapper;

import com.leo.pojo.TravelSearch;
import com.leo.utils.MyMapper;

import java.util.List;

public interface TravelSearchMapper extends MyMapper<TravelSearch> {

    List<String> getHotKey();
}