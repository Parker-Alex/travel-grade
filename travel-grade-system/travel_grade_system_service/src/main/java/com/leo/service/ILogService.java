package com.leo.service;

import com.leo.pojo.TravelLog;

import java.util.List;

public interface ILogService {

    int addLog(TravelLog travelLog);

    int updateLog(TravelLog travelLog);

    List<TravelLog> getLogsByUserId(String userId);
}
