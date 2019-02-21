package com.leo.service.impl;

import com.leo.mapper.TravelLogMapper;
import com.leo.pojo.TravelLog;
import com.leo.service.ILogService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class LogServiceImpl implements ILogService {

    @Autowired
    private TravelLogMapper logMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int addLog(TravelLog travelLog) {
        travelLog.setId(sid.nextShort());
        return logMapper.insert(travelLog);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateLog(TravelLog travelLog) {
//        该更新方法只更新对象中值为null的属性
        return logMapper.updateByPrimaryKeySelective(travelLog);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelLog> getLogsByUserId(String userId) {
        Example example = new Example(TravelLog.class);
//        设置按登录时间降序排序
        example.setOrderByClause("login_time desc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        return logMapper.selectByExample(example);
    }
}
