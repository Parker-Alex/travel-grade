package com.leo.service.impl;

import com.leo.mapper.TravelRecommendMapper;
import com.leo.pojo.TravelRecommend;
import com.leo.service.IRecommendService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private Sid sid;

    @Autowired
    private TravelRecommendMapper recommendMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelRecommend> getUserRecommends(String userId) {
        return recommendMapper.getUserRecommends(userId);
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

        if (result < 0) {
            throw new RuntimeException();
        }
        return result;
    }
}
