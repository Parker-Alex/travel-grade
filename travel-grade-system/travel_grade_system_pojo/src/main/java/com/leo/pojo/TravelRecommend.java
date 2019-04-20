package com.leo.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "leo_travel_system_recommend")
public class TravelRecommend {
    @Id
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "city_image")
    private String cityImage;

    private String reason;

    @Column(name = "province_name")
    private String provinceName;

}