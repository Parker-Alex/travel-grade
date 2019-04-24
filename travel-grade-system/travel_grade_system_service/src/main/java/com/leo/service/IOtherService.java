package com.leo.service;

import com.leo.pojo.TravelOther;

import java.util.List;
import java.util.Map;

/**
 * @ClassName IOtherService
 * @Description TODO
 * @Author li.jiawei
 * @Date 2019/4/23 19:31
 * @Version 1.0
 */
public interface IOtherService {

    List<Double> getOthersGrade(String cityId);

    List<TravelOther> getOtherUserGrade(String cityId, String userId);

    void updateOther(String cityId, String userId, String body);

    List<TravelOther> getOthers(String cityId, String userId);
}
