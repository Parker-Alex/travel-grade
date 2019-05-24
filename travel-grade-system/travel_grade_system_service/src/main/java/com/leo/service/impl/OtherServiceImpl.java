package com.leo.service.impl;

import com.leo.mapper.TravelOtherMapper;
import com.leo.pojo.TravelOther;
import com.leo.service.IOtherService;
import com.leo.utils.JacksonUtil;
import org.n3r.idworker.Sid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName OtherServiceImpl
 * @Description TODO
 * @Author li.jiawei
 * @Date 2019/4/23 19:32
 * @Version 1.0
 */
@Service
public class OtherServiceImpl implements IOtherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OtherServiceImpl.class);

    @Autowired
    private TravelOtherMapper otherMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Double> getOthersGrade(String cityId) {
        List<Double> list = new ArrayList<>();

        Double fruits = otherMapper.getOtherGrade(cityId, 0);
        Double traffic = otherMapper.getOtherGrade(cityId, 1);
        Double weather = otherMapper.getOtherGrade(cityId, 2);
        Double cate = otherMapper.getOtherGrade(cityId, 3);
        Double stay = otherMapper.getOtherGrade(cityId, 4);

        if (fruits == null) {
            fruits = 0D;
        }
        if (traffic == null) {
            traffic = 0D;
        }
        if (weather == null) {
            weather = 0D;
        }
        if (cate == null) {
            cate = 0D;
        }
        if (stay == null) {
            stay = 0D;
        }

        list.add(fruits);
        list.add(traffic);
        list.add(weather);
        list.add(cate);
        list.add(stay);

        return list;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelOther> getOtherUserGrade(String cityId, String userId) {
        Example example = new Example(TravelOther.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("cityId", cityId);
        criteria.andEqualTo("userId", userId);
        example.setOrderByClause("type ASC");
        return otherMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateOther(String cityId, String userId, String body) {
//        获得对象
        List<TravelOther> others = getOthers(cityId, userId);

        TravelOther other0 = null;
        TravelOther other1 = null;
        TravelOther other2 = null;
        TravelOther other3 = null;
        TravelOther other4 = null;

        Double grade0 = JacksonUtil.parseDouble(body, "grade0");
        Double grade1 = JacksonUtil.parseDouble(body, "grade1");
        Double grade2 = JacksonUtil.parseDouble(body, "grade2");
        Double grade3 = JacksonUtil.parseDouble(body, "grade3");
        Double grade4 = JacksonUtil.parseDouble(body, "grade4");

//        如果没有对象，则为插入操作，否则为更新操作
        if (others == null || others.size() <= 0) {
            List<TravelOther> list = new ArrayList<>();

            other0 = createOther(cityId, userId, 0, grade0);
            list.add(other0);
            otherMapper.insertSelective(other0);

            other1 = createOther(cityId, userId, 1, grade1);
            list.add(other1);
            otherMapper.insertSelective(other1);

            other2 = createOther(cityId, userId, 2, grade2);
            list.add(other2);
            otherMapper.insertSelective(other2);

            other3 = createOther(cityId, userId, 3, grade3);
            list.add(other3);
            otherMapper.insertSelective(other3);

            other4 = createOther(cityId, userId, 4, grade4);
            list.add(other4);
            otherMapper.insertSelective(other4);

        } else {
            int result = 0;

            if (grade0 != null) {
                other0 = others.get(0);
                other0.setGrade(grade0);
                result = otherMapper.updateByPrimaryKey(other0);
            }
            if (grade1 != null) {
                other1 = others.get(1);
                other1.setGrade(grade1);
                result = otherMapper.updateByPrimaryKey(other1);
            }
            if (grade2 != null) {
                other2 = others.get(2);
                other2.setGrade(grade2);
                result = otherMapper.updateByPrimaryKey(other2);
            }
            if (grade3 != null) {
                other3 = others.get(3);
                other3.setGrade(grade3);
                result = otherMapper.updateByPrimaryKey(other3);
            }
            if (grade4 != null) {
                other4 = others.get(4);
                other4.setGrade(grade4);
                result = otherMapper.updateByPrimaryKey(other4);
            }

            if (result <= 0) {
                LOGGER.error("更新其他服务用户评分失败");
                throw new RuntimeException();
            }
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelOther> getOthers(String cityId, String userId) {
        Example example = new Example(TravelOther.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("cityId", cityId);
        example.setOrderByClause("type ASC");
        return otherMapper.selectByExample(example);
    }

    private TravelOther createOther(String cityId, String userId, int type, Double grade) {
        TravelOther other = new TravelOther();
        other.setId(sid.nextShort());
        other.setCityId(cityId);
        other.setUserId(userId);
        other.setType(type);
        other.setGrade(grade);

        return other;
    }
}
