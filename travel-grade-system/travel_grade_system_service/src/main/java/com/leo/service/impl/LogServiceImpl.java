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

@Service
public class LogServiceImpl implements ILogService {

    @Autowired
    private TravelLogMapper logMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public TravelLog getLogByUserId(String userId) {
        Example example = new Example(TravelLog.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        return logMapper.selectOneByExample(example);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int addLog(TravelLog travelLog) {
        travelLog.setId(sid.nextShort());
        return logMapper.insert(travelLog);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateLog(TravelLog travelLog) {
        return logMapper.updateByPrimaryKey(travelLog);
    }
}
