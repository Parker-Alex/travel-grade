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

    /**
     * @Author li.jiawei
     * @Description 通过操作类型得到人数，0 => 想去，1 => 点赞，2 => 去过
     * @Date 19:46 2019/3/23
     */
    int getCountByType(int type, String cityId);

    double getAvgGrade(String cityId);
}