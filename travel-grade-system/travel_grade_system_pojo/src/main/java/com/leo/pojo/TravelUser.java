package com.leo.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "leo_travel_system_user")
public class TravelUser {
    @Id
    private String id;

    private String username;

    private String password;

    private String nickname;

    private String avatar;

    @Column(name = "title_name")
    private String titleName;

    @Column(name = "follow_count")
    private Integer followCount;

    @Column(name = "recommend_count")
    private Integer recommendCount;

    @Column(name = "commend_count")
    private Integer commendCount;

    @Column(name = "open_id")
    private String openId;

    private Integer level;

    private Boolean deleted;

    @Column(name = "add_time")
    private Date addTime;

    @Column(name = "update_time")
    private Date updateTime;

    private String mobile;

    private Byte gender;

}