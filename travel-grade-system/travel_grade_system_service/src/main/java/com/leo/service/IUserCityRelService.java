package com.leo.service;

import com.leo.pojo.TravelUserCityRel;

/**
 * @ClassName IUserCityRelService
 * @Description TODO
 * @Author li.jiawei
 * @Date 2019/3/22 14:54
 * @Version 1.0
 */
public interface IUserCityRelService {

    TravelUserCityRel getRelByUserIdAndCityId(String userId, String cityId);

    int updateRel(TravelUserCityRel userCityRel);

    int insertRel(TravelUserCityRel userCityRel);
}
