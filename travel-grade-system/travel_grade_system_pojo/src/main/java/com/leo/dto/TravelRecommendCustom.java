package com.leo.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
public class TravelRecommendCustom {
    private String id;

    private String userId;

    private String cityName;

    private String cityImage;

    private String reason;

    private String provinceName;

    private String cityId;

}