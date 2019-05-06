package com.leo.service.impl;

import com.leo.mapper.TravelCityMapper;
import com.leo.mapper.TravelRecommendCustomMapper;
import com.leo.mapper.TravelRecommendMapper;
import com.leo.mapper.TravelUserMapper;
import com.leo.pojo.TravelCity;
import com.leo.pojo.TravelRecommend;
import com.leo.pojo.TravelUser;
import com.leo.service.IRecommendService;
import org.n3r.idworker.Sid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @ClassName RecommendServiceImpl
 * @Description TODO
 * @Author li.jiawei
 * @Date 2019/4/18 15:55
 * @Version 1.0
 */
@Service
public class RecommendServiceImpl implements IRecommendService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecommendServiceImpl.class);

    @Autowired
    private Sid sid;

    @Autowired
    private TravelRecommendMapper recommendMapper;

    @Autowired
    private TravelUserMapper userMapper;

    @Autowired
    private TravelCityMapper cityMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelCity> getUserRecommends(String userId) {
        List<TravelCity> cs = cityMapper.userRecommendCities(userId);
        for (TravelCity c : cs) {
            if (c.getIntroduce() != null && c.getIntroduce().length() > 18) {
                String shortIntroduce = c.getIntroduce().substring(0, 18) + "...";
                c.setIntroduce(shortIntroduce);
            }
        }
        return cs;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int addRecommend(String userId, String reason, String provinceName, String cityName, String httpPath) {
        TravelRecommend recommend = new TravelRecommend();
        recommend.setId(sid.nextShort());
        recommend.setUserId(userId);
        recommend.setProvinceName(provinceName);
        recommend.setCityName(cityName);
        recommend.setCityImage(httpPath);
        recommend.setReason(reason);
        int result = recommendMapper.insert(recommend);

        if (result <= 0) {
            LOGGER.error("添加推荐城市出错");
            throw new RuntimeException("添加推荐城市出错");
        }

//        根据推荐城市的内容为用户添加称号
        int count = getRecommendCount(userId);
        if (count > 20) {
            TravelUser user = userMapper.selectByPrimaryKey(userId);
            /**
             * TODO 可以将用户称号用枚举类实现
             */
            user.setTitleName("推荐小达人");
            userMapper.updateByPrimaryKey(user);
        }

        return result;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public int getRecommendCount(String userId) {
        return recommendMapper.getRecommendCount(userId);
    }
}
