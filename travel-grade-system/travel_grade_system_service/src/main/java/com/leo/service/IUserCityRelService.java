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

    /**
     * @Author li.jiawei
     * @Description 通过用户不为0的评分，计算城市的平均评分
     * @Date 1:43 2019/4/17
     */
    Double getAvgGrade(String cityId);

    /**
     * @Author li.jiawei
     * @Description 对用户城市关系进行判断，以便决定是插入关系还是更新关系
     * @Date 11:32 2019/4/2
     */
    int judgeRel(TravelUserCityRel userCityRelNew, TravelUserCityRel userCityRel, String userId);
}
