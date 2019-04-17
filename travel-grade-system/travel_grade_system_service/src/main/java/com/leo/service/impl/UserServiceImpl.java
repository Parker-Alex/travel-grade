package com.leo.service.impl;

import com.leo.mapper.TravelUserMapper;
import com.leo.pojo.TravelUser;
import com.leo.service.IUserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private TravelUserMapper userMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int addUser(TravelUser user) {
        user.setId(sid.nextShort());
        return userMapper.insertSelective(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public TravelUser getUserByOpenId(String openId) {
        Example example = new Example(TravelUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("openId", openId);
        return userMapper.selectOneByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelUser> getUsersByOpenId(String openId) {
        Example example = new Example(TravelUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("openId", openId);
        return userMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelUser> getUsersByUsername(String username) {
        Example example = new Example(TravelUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        return userMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelUser> getUsersByMobile(String mobile) {
        Example example = new Example(TravelUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("mobile", mobile);
        return userMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public TravelUser getUserByUserId(String userId) {
        return userMapper.selectByPrimaryKey(userId);
    }
}
