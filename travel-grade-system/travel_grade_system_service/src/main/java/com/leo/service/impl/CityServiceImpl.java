package com.leo.service.impl;

import com.github.pagehelper.PageHelper;
import com.leo.dto.TravelCityCustom;
import com.leo.mapper.TravelCityCustomMapper;
import com.leo.mapper.TravelCityMapper;
import com.leo.pojo.TravelCity;
import com.leo.service.ICityService;
import com.leo.service.IUserCityRelService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
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
    private IUserCityRelService userCityRelService;

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
        TravelCityCustom city = list.get(0);
        if (city == null) {
            return null;
        }
        return city;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<String> getAllName() {
        return cityMapper.getAllName();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateCity(String cityId) {
//        得到原有城市记录
        TravelCity city = cityMapper.selectByPrimaryKey(cityId);
        if (city == null) {
            return 0;
        }
//        将原有城市数据复制到新的城市对象中
        TravelCity cityNew = new TravelCity();
        BeanUtils.copyProperties(city, cityNew);
//        设置城市相关操作人数
        int likeCount = userCityRelService.getCountByType(0, cityId);
        int favourCount = userCityRelService.getCountByType(1, cityId);
        int goneCount = userCityRelService.getCountByType(2, cityId);
        int gradeCount = userCityRelService.getCountByType(3, cityId);
//        获得城市评分
        Double grade = userCityRelService.getAvgGrade(cityId);
//        因为该属性是包装类，所以要进行判空
        if (grade == null) {
            grade = 0D;
        }

        cityNew.setLikeCount(likeCount);
        cityNew.setFavourCount(favourCount);
        cityNew.setGoneCount(goneCount);
        cityNew.setGradeCount(gradeCount);
        cityNew.setGrade(grade);
        return cityMapper.updateByPrimaryKey(cityNew);
    }
}
