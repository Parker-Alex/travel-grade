package com.leo.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "leo_travel_system_province")
public class TravelProvince {
    @Id
    private String id;

    private String name;

    private Double grade;

    @Column(name = "city_count")
    private Integer cityCount;

    private String introduce;

    private String cover;

    private String reason;

}