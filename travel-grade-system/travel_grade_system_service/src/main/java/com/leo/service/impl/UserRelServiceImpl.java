package com.leo.service.impl;

import com.leo.dto.TravelUserRelCustom;
import com.leo.mapper.TravelUserRelCustomMapper;
import com.leo.mapper.TravelUserRelMapper;
import com.leo.pojo.TravelUserRel;
import com.leo.service.IUserRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @ClassName UserRelServiceImpl
 * @Description TODO
 * @Author li.jiawei
 * @Date 2019/4/18 15:34
 * @Version 1.0
 */
@Service
public class UserRelServiceImpl implements IUserRelService {

    @Autowired
    private TravelUserRelMapper userRelMapper;

    @Autowired
    private TravelUserRelCustomMapper userRelCustomMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelUserRel> getFans(String userId) {
        return userRelMapper.getFans(userId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public int getFollow(String userId) {
        return userRelMapper.getFollow(userId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelUserRelCustom> getMyFollows(String userId) {
        List<TravelUserRelCustom> follows = userRelCustomMapper.getMyFollows(userId);
        for(TravelUserRelCustom f : follows) {
            // 格式化日期
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            f.setDate(format.format(f.getAttentionDate()));
        }
        return follows;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelUserRelCustom> getMyFans(String userId) {
        List<TravelUserRelCustom> fans = userRelCustomMapper.getMyFans(userId);
        for(TravelUserRelCustom f : fans) {
            // 格式化日期
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            f.setDate(format.format(f.getAttentionDate()));
        }
        return fans;
    }
}
