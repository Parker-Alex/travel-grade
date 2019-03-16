package com.leo.service.impl;

import com.github.pagehelper.PageHelper;
import com.leo.mapper.TravelSearchMapper;
import com.leo.pojo.TravelSearch;
import com.leo.service.ISearchService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class SearchServiceImpl implements ISearchService {

    @Autowired
    private TravelSearchMapper searchMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<String> getHotKeys() {
        PageHelper.startPage(0, 5);
        return searchMapper.getHotKey();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addHotKey(String name) {
        TravelSearch search = new TravelSearch();
        search.setId(sid.nextShort());
        search.setContent(name);
        searchMapper.insertSelective(search);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public TravelSearch isExisting(String name) {
        Example example = new Example(TravelSearch.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("content", name);
        return searchMapper.selectOneByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void deleteSearchByContent(String content) {
        Example example = new Example(TravelSearch.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("content", content);
        searchMapper.deleteByExample(example);
    }

}
