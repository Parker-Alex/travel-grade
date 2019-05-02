package com.leo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leo.mapper.TravelCityMapper;
import com.leo.mapper.TravelProvinceMapper;
import com.leo.pojo.TravelCity;
import com.leo.pojo.TravelProvince;
import com.leo.service.IProvinceService;
import com.leo.utils.JacksonUtil;
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

    @Autowired
    private TravelCityMapper cityMapper;

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

    @Override
    public PageInfo<TravelProvince> getProvincesByAdmin(Integer pageNum, Integer pageSize) {
//        按评分进行排序
        Example example = new Example(TravelProvince.class);
        example.setOrderByClause("grade desc");

        PageHelper.startPage(pageNum, pageSize);

        List<TravelProvince> ps = provinceMapper.selectByExample(example);
        return new PageInfo<>(ps);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int addProvince(TravelProvince province) {
        province.setId(sid.nextShort());
        return provinceMapper.insertSelective(province);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<String> getAllName() {
        return provinceMapper.getAllName();
    }

    @Override
    public TravelProvince getProvince(String body) {
        String name = JacksonUtil.parseString(body, "name");
        String id = JacksonUtil.parseString(body, "id");

//        判空
        if (id.equals("null")) {
            id = null;
        }
        if (name.equals("null")) {
            name = null;
        }

        return provinceMapper.getProvince(name, id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateProvinceByCityId(String cityId) {
        TravelCity city = cityMapper.selectByPrimaryKey(cityId);
        TravelProvince province = provinceMapper.selectByPrimaryKey(city.getProvinceId());
        Double grade = this.getAvgGrade(city.getProvinceId());
        if (grade == null) {
            grade = 0D;
        }
        province.setGrade(grade);
        int result = provinceMapper.updateByPrimaryKey(province);

        if (result <= 0) {
            throw new RuntimeException("更新省份评分失败");
        }
        return result;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Double getAvgGrade(String provinceId) {
        return provinceMapper.getAvgGrade(provinceId);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public TravelProvince getProvinceById(String id) {
        return provinceMapper.selectByPrimaryKey(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int addProvinceByAdmin(TravelProvince province) {
        province.setId(sid.nextShort());
        int result = provinceMapper.insertSelective(province);

        if (result <= 0) {
            throw new RuntimeException("后台添加省份失败");
        }

        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateProvinceByAdmin(TravelProvince newProvince) {
        TravelProvince province = provinceMapper.selectByPrimaryKey(newProvince.getId());
        boolean flag = false;
        int result = 0;
//        如果名称被修改
        if (!province.getName().equals(newProvince.getName())) {
            province.setName(newProvince.getName());
            flag = true;
        }
//        如果名称由来被修改
        if (!province.getReason().equals(newProvince.getReason())) {
            province.setReason(newProvince.getReason());
            flag = true;
        }
//        如果简介被修改
        if (!province.getIntroduce().equals(newProvince.getIntroduce())) {
            province.setIntroduce(newProvince.getIntroduce());
            flag = true;
        }
//        如果进行了修改操作
        if (flag) {
            result = provinceMapper.updateByPrimaryKey(province);
        }

        if (result <= 0) {
            throw new RuntimeException("后台修改省份失败");
        }
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int deleteProvinceById(String provinceId) {
        int result = provinceMapper.deleteByPrimaryKey(provinceId);
        if (result <= 0) {
            throw new RuntimeException("删除省份失败");
        }
        return result;
    }
}
