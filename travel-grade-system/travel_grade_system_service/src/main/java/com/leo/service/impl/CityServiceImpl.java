package com.leo.service.impl;

import com.github.pagehelper.PageHelper;
import com.leo.dto.TravelCityCustom;
import com.leo.mapper.TravelCityCustomMapper;
import com.leo.mapper.TravelCityMapper;
import com.leo.pojo.TravelCity;
import com.leo.service.ICityService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CityServiceImpl implements ICityService {

    @Autowired
    private TravelCityCustomMapper cityCustomMapper;

    @Autowired
    private TravelCityMapper cityMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelCity> getHotCities() {

//        设置分页参数
        PageHelper.startPage(1, 10);

        Example example = new Example(TravelCity.class);
        example.setOrderByClause("grade desc");

        return cityMapper.selectByExample(example);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelCity> getCities(Integer pageNum, Integer pageSize) {

//        设置分页参数
        PageHelper.startPage(pageNum, pageSize);

//        获得城市列表
        return cityMapper.selectAll();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int addCity(TravelCity city) {
        city.setId(sid.nextShort());
        return cityMapper.insertSelective(city);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelCity> getCitiesByProvinceId(String provinceId) {
        Example example = new Example(TravelCity.class);
        example.setOrderByClause("grade desc");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("provinceId", provinceId);
        return cityMapper.selectByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public TravelCityCustom getCity(String cityId, String name) {
//        Example example = new Example(TravelCity.class);
//        example.setOrderByClause("grade desc");
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andLike("name", "%" + name + "%");
//        return cityMapper.selectByExample(example).get(0);
//        TravelCityCustom cityCustom = new TravelCityCustom();
//        cityCustom.setName(name);
//        cityCustom.setId(cityId);
        List<TravelCityCustom> list = cityCustomMapper.getCity(cityId, name);
        if (list.size() <= 0) {
            return null;
        }
        for (TravelCityCustom c : list) {
            System.out.println(c);
        }
        return list.get(0);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<String> getAllName() {
        return cityMapper.getAllName();
    }
}
