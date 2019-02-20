package com.leo.service;

import com.leo.pojo.TravelLog;

public interface ILogService {

    TravelLog getLogByUserId(String userId);

    int addLog(TravelLog travelLog);

    int updateLog(TravelLog travelLog);
}
