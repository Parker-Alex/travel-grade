package com.leo.service.impl;

import com.leo.mapper.TravelRecommendMapper;
import com.leo.pojo.TravelRecommend;
import com.leo.service.IRecommendService;
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
    private TravelRecommendMapper recommendMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelRecommend> getUserRecommends(String userId) {
        return recommendMapper.getUserRecommends(userId);
    }
}
