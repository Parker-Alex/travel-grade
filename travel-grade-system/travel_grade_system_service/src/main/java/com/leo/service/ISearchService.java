package com.leo.service;

import com.leo.pojo.TravelSearch;

import java.util.List;

public interface ISearchService {

    List<String> getHotKeys();

    void addHotKey(String name);

    TravelSearch isExisting(String name);

    void deleteSearchByContent(String content);
}
