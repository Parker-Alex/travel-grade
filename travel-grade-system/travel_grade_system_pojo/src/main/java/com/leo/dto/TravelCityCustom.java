package com.leo.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author leo
 * @Description 数据传输类，包装TravelCity对象，还有所对应的省份
 * @Date 23:05 2019/3/4
 */
@Data
public class TravelCityCustom {

    private String id;

    private String name;

    private String provinceId;

    private Integer favourCount;

    private Integer commendCount;

    private Integer likeCount;

    private Integer gradeCount;

    private Integer goneCount;

    private Double grade;

    private String introduce;

    private String cover;

    private String provinceName;

}