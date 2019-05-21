package com.leo.service.impl;

import com.leo.dto.TravelUserRelCustom;
import com.leo.mapper.TravelUserRelCustomMapper;
import com.leo.mapper.TravelUserRelMapper;
import com.leo.pojo.TravelUserRel;
import com.leo.service.IUserRelService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private Sid sid;

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

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean isFollow(String userId, String toUserId) {
        Example example = new Example(TravelUserRel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("attentionUserId", toUserId);
        TravelUserRel userRel = userRelMapper.selectOneByExample(example);
        if (userRel == null) {
            return false;
        }
        return true;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int createRel(String userId, String toUserId) {
        TravelUserRel userRel = new TravelUserRel();
        userRel.setId(sid.nextShort());
        userRel.setUserId(userId);
        userRel.setAttentionUserId(toUserId);
        userRel.setAttentionDate(new Date());
        int result = userRelMapper.insert(userRel);
        if (result <= 0) {
            throw new RuntimeException("建立用户与用户关系失败");
        }
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int deleteRel(String userId, String toUserId) {
        Example example = new Example(TravelUserRel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("attentionUserId", toUserId);
        int result = userRelMapper.deleteByExample(example);
        if (result <= 0) {
            throw new RuntimeException("删除用户与用户关系失败");
        }
        return result;
    }
}
