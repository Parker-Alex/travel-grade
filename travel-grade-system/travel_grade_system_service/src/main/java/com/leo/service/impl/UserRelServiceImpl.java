package com.leo.service.impl;

import com.leo.mapper.TravelUserRelMapper;
import com.leo.pojo.TravelUserRel;
import com.leo.service.IUserRelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelUserRel> getFans(String userId) {
        return userRelMapper.getFans(userId);
    }
}
