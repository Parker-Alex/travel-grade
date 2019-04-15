package com.leo.mapper;

import com.leo.pojo.TravelProvince;
import com.leo.utils.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface TravelProvinceMapper extends MyMapper<TravelProvince> {

//    得到所有省份名字
    List<String> getAllName();

    /**
     * @Author li.jiawei
     * @Description 根据省份名字或省份id查询省份对象
     * @Date 16:13 2019/4/15
     */
    TravelProvince getProvince(@Param("name") String name, @Param("id") String id);
}