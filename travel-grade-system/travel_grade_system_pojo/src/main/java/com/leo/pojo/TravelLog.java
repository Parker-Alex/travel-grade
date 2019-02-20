package com.leo.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "leo_travel_system_log")
public class TravelLog {
    @Id
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "login_time")
    private Date loginTime;

    @Column(name = "logout_time")
    private Date logoutTime;

    @Column(name = "login_ip")
    private String loginIp;

    @Column(name = "login_city")
    private String loginCity;

    @Column(name = "login_province")
    private String loginProvince;

    @Column(name = "login_country")
    private String loginCountry;

}