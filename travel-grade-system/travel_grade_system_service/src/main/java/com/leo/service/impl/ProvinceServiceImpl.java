package com.leo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leo.mapper.TravelProvinceMapper;
import com.leo.pojo.TravelProvince;
import com.leo.service.IProvinceService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class ProvinceServiceImpl implements IProvinceService {

    @Autowired
    private TravelProvinceMapper provinceMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelProvince> getHotProvinces() {

        PageHelper.startPage(1, 10);

        Example example = new Example(TravelProvince.class);
        example.setOrderByClause("grade desc");

        return provinceMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PageInfo<TravelProvince> getProvinces(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

//        按评分进行排序
        Example example = new Example(TravelProvince.class);
        example.setOrderByClause("grade desc");

        List<TravelProvince> ps = provinceMapper.selectByExample(example);
//        简化简介，方便展示美观
        for (TravelProvince p : ps) {
            if(p.getIntroduce().length() > 18) {
                String shortIntroduce = p.getIntroduce().substring(0, 18) + "...";
                p.setIntroduce(shortIntroduce);
            }
        }

        PageInfo<TravelProvince> provinces = new PageInfo<>(ps);
        return provinces;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int addProvince(TravelProvince province) {
        province.setId(sid.nextShort());
        return provinceMapper.insertSelective(province);
    }
}
