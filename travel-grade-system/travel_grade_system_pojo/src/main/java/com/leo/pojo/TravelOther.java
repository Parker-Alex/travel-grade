package com.leo.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "leo_travel_system_other")
public class TravelOther {
    @Id
    private String id;

    private String name;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "city_id")
    private String cityId;

    private Integer type;

    private Double grade;

}