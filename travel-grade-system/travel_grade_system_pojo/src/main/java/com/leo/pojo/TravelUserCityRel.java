package com.leo.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "leo_travel_system_user_city_rel")
public class TravelUserCityRel {
    @Id
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "city_id")
    private String cityId;

    private Double grade;

    @Column(name = "is_favour")
    private Boolean isFavour;

    @Column(name = "is_like")
    private Boolean isLike;

    @Column(name = "is_gone")
    private Boolean isGone;

}