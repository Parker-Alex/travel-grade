package com.leo.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "leo_travel_system_city")
public class TravelCity {
    @Id
    private String id;

    private String name;

    @Column(name = "province_id")
    private String provinceId;

    private String introduce;

    private String cover;

    private Double grade;

    @Column(name = "commend_count")
    private Integer commendCount;

    @Column(name = "favour_count")
    private Integer favourCount;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "grade_count")
    private Integer gradeCount;

    @Column(name = "gone_count")
    private Integer goneCount;


}