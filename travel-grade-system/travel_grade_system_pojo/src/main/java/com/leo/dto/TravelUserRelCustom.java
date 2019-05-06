package com.leo.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Data
public class TravelUserRelCustom {

    private String id;

    private String userId;

    private String attentionUserId;

    private Date attentionDate;

    private String name;

    private String avatar;

    /**
     * 不进行映射
     */
    @Transient
    private String date;
}