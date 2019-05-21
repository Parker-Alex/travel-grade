package com.leo.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "leo_travel_system_user_rel")
public class TravelUserRel {
    @Id
    private String id;

    @Column(name = "user_id")
    private String userId;

    /**
     * 关注的用户id
     */
    @Column(name = "attention_user_id")
    private String attentionUserId;

    /**
     * 关注日期
     */
    @Column(name = "attention_date")
    private Date attentionDate;

}