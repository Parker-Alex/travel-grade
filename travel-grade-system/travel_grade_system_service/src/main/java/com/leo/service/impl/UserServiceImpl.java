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

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public TravelUser getUserByOpenId(String openId) {
        Example example = new Example(TravelUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("opendId", openId);
        return userMapper.selectOneByExample(example);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int addUser(TravelUser user) {
        user.setId(sid.nextShort());
        return userMapper.insert(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelUser> getUsersByUsername(String username) {
        Example example = new Example(TravelUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        return userMapper.selectByExample(example);
    }
}
