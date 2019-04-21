package com.leo.service.impl;

import com.leo.mapper.TravelUserCityRelMapper;
import com.leo.pojo.TravelUserCityRel;
import com.leo.service.ICityService;
import com.leo.service.IUserCityRelService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

/**
 * @ClassName UserCityRelServiceImpl
 * @Description TODO
 * @Author li.jiawei
 * @Date 2019/3/22 14:56
 * @Version 1.0
 */
@Service
public class UserCityRelServiceImpl implements IUserCityRelService {

    @Autowired
    private Sid sid;

    @Autowired
    private TravelUserCityRelMapper userCityRelMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public TravelUserCityRel getRelByUserIdAndCityId(String userId, String cityId) {
        Example example = new Example(TravelUserCityRel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("cityId", cityId);
        return userCityRelMapper.selectOneByExample(example);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateRel(TravelUserCityRel userCityRel) {
        return userCityRelMapper.updateByPrimaryKeySelective(userCityRel);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int insertRel(TravelUserCityRel userCityRel) {
        userCityRel.setId(sid.nextShort());
        return userCityRelMapper.insertSelective(userCityRel);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public int getCountByType(int type, String cityId) {
        return userCityRelMapper.getCountByType(type, cityId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Double getAvgGrade(String cityId) {
        return userCityRelMapper.getAvgGrade(cityId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public int judgeRel(TravelUserCityRel userCityRelNew, TravelUserCityRel userCityRel, String userId) {
        int result = 0;
//        用户第一次与城市建立关系，则新建关系
        if (userCityRelNew == null) {
            userCityRel.setUserId(userId);
            result = this.insertRel(userCityRel);
        } else {
//            用户不是第一次建立关系，则在原来关系的基础上修改
            userCityRelNew.setIsFavour(userCityRel.getIsFavour());
            userCityRelNew.setIsGone(userCityRel.getIsGone());
            userCityRelNew.setIsLike(userCityRel.getIsLike());
            userCityRelNew.setGrade(userCityRel.getGrade());
            result = this.updateRel(userCityRelNew);
        }

        if (result <= 0) {
            throw new RuntimeException();
        }
        return result;
    }
}
