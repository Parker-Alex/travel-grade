package com.leo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leo.dto.TravelCityCustom;
import com.leo.enums.CityCondition;
import com.leo.mapper.TravelCityCustomMapper;
import com.leo.mapper.TravelCityMapper;
import com.leo.mapper.TravelProvinceMapper;
import com.leo.pojo.TravelCity;
import com.leo.pojo.TravelProvince;
import com.leo.service.ICityService;
import com.leo.service.IProvinceService;
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

    @Autowired
    private TravelProvinceMapper provinceMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public TravelCity getCityByName(String name) {
        Example example = new Example(TravelCity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", name);
        return cityMapper.selectOneByExample(example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelCity> getHotCities() {
        return pageByProperty("grade", "desc");
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelCity> getCitiesByCommendCount() {
        return pageByProperty("commend_count", "desc");
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelCity> getCitiesByFavourCount() {
        return pageByProperty("favour_count", "desc");
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelCity> getCitiesByLikeCount() {
        return pageByProperty("like_count", "desc");
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelCity> getCitiesByGoneCount() {
        return pageByProperty("gone_count", "desc");
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelCity> getCitiesByGradeCount() {
        return pageByProperty("grade_count", "desc");
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PageInfo<TravelCity> getCityList(Integer index, Integer pageNum) {

        if (pageNum == null || pageNum == 0) {
            pageNum = 1;
        }

//        设置分页参数
        PageHelper.startPage(pageNum, 4);

        Example example = new Example(TravelCity.class);
//        example.setOrderByClause("grade desc");
        example.setOrderByClause(CityCondition.getProperty(index) + "  desc");

        List<TravelCity> cs = cityMapper.selectByExample(example);

//        将城市的简介进行简化
        for (TravelCity c : cs) {
            if (c.getIntroduce() != null && c.getIntroduce().length() > 18) {
                String shortIntroduce = c.getIntroduce().substring(0, 18) + "...";
                c.setIntroduce(shortIntroduce);
            }
        }
//        使用PageInfo对城市列表进行封装
        PageInfo<TravelCity> cities = new PageInfo<>(cs);
        return cities;
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
        int result = cityMapper.insertSelective(city);
        if (result <= 0) {
            throw new RuntimeException("添加城市失败");
        }

//        更新省份所拥有城市数
        increaseCount(city.getProvinceId());

        return result;
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

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelCity> userGoneCities(String userId) {
        List<TravelCity> cs = cityMapper.userGoneCities(userId);
        for (TravelCity c : cs) {
            if (c.getIntroduce() != null && c.getIntroduce().length() > 17) {
                String shortIntroduce = c.getIntroduce().substring(0, 17) + "...";
                c.setIntroduce(shortIntroduce);
            }
        }
        return cs;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelCity> userLikeCities(String userId) {
        List<TravelCity> cs = cityMapper.userLikeCities(userId);
        for (TravelCity c : cs) {
            if (c.getIntroduce() != null && c.getIntroduce().length() > 17) {
                String shortIntroduce = c.getIntroduce().substring(0, 17) + "...";
                c.setIntroduce(shortIntroduce);
            }
        }
        return cs;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<TravelCity> userFavourCities(String userId) {
        List<TravelCity> cs = cityMapper.userFavourCities(userId);
        for (TravelCity c : cs) {
            if (c.getIntroduce() != null && c.getIntroduce().length() > 17) {
                String shortIntroduce = c.getIntroduce().substring(0, 17) + "...";
                c.setIntroduce(shortIntroduce);
            }
        }
        return cs;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public TravelCity getCityByCityId(String cityId) {
        return cityMapper.selectByPrimaryKey(cityId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int updateCityByAdmin(TravelCity newCity) {
        TravelCity city = cityMapper.selectByPrimaryKey(newCity.getId());
        int result = 0;
        boolean flag = false;

//        如果名字被修改
        if (!city.getName().equals(newCity.getName())) {
            city.setName(newCity.getName());
            flag = true;
        }
//        如果所属省份被修改
        if (!city.getProvinceId().equals(newCity.getProvinceId())) {
            city.setProvinceId(newCity.getProvinceId());
            flag = true;

            /**
             * 如果所属省份id修改了，那么要将原来省份的城市数-1，新省份城市数+1
             */
            String oldPId = city.getProvinceId();
            String newPId = newCity.getProvinceId();

            decreaseCount(oldPId);
            increaseCount(newPId);

        }
//        如果封面被修改
        if (city.getCover() == null || !city.getCover().equals(newCity.getCover())) {
            city.setCover(newCity.getCover());
            flag = true;
        }
//        如果简介被修改
        if (!city.getIntroduce().equals(newCity.getIntroduce())) {
            city.setIntroduce(newCity.getIntroduce());
            flag = true;
        }

        if (flag) {
            result = cityMapper.updateByPrimaryKey(city);
        }
        if (result <= 0) {
            throw new RuntimeException("后台修改城市失败");
        }
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int deleteCityByCityId(String cityId) {
        TravelCity city = cityMapper.selectByPrimaryKey(cityId);
        String provinceId = city.getProvinceId();

        int result = cityMapper.deleteByPrimaryKey(cityId);

        if (result <= 0) {
            throw new RuntimeException("删除城市失败");
        }

//        更新省份所拥有城市数
        decreaseCount(provinceId);
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int deleteAllByProvinceId(String provinceId) {
        Example example = new Example(TravelCity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("provinceId", provinceId);
        int result = cityMapper.deleteByExample(example);
        if (result <= 0) {
            throw new RuntimeException("删除省份所拥有城市失败");
        }
        return result;
    }

    /**
     * @Author li.jiawei
     * @Description 按照城市的不同属性进行分页查询城市列表，
     * @Param str1 进行排序的列名，str2 排序的方式
     * @Date 14:19 2019/4/13
     */
    private List<TravelCity> pageByProperty(String str1, String str2) {

//        设置分页参数
        PageHelper.startPage(1, 5);

        Example example = new Example(TravelCity.class);
//        example.setOrderByClause("grade desc");
        example.setOrderByClause(str1 + " " + str2);

        List<TravelCity> cs = cityMapper.selectByExample(example);

//        将城市的简介进行简化
        for (TravelCity c : cs) {
            if (c.getIntroduce() != null && c.getIntroduce().length() > 17) {
                String shortIntroduce = c.getIntroduce().substring(0, 17) + "...";
                c.setIntroduce(shortIntroduce);
            }
        }
        return cs;
    }

    /**
     * @Author li.jiawei
     * @Description 减少省份拥有城市数
     * @Date 17:09 2019/5/1
     */
    private void decreaseCount(String provinceId) {
        TravelProvince province = provinceMapper.selectByPrimaryKey(provinceId);
        int oldCityCount = province.getCityCount();
        province.setCityCount(oldCityCount - 1);
        int result = provinceMapper.updateByPrimaryKey(province);

        if (result <= 0) {
            throw new RuntimeException("减少省份拥有城市数失败");
        }
    }

    /**
     * @Author li.jiawei
     * @Description 增加省份拥有城市数
     * @Date 17:14 2019/5/1
     */
    private void increaseCount(String provinceId) {
        TravelProvince province = provinceMapper.selectByPrimaryKey(provinceId);
        int oldCityCount = province.getCityCount();
        province.setCityCount(oldCityCount + 1);
        int result = provinceMapper.updateByPrimaryKey(province);

        if (result <= 0) {
            throw new RuntimeException("增加省份拥有城市数失败");
        }
    }

}
