package com.leo.pojo;

import java.util.Date;
import javax.persistence.*;

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

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return user_id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * @return login_time
     */
    public Date getLoginTime() {
        return loginTime;
    }

    /**
     * @param loginTime
     */
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    /**
     * @return logout_time
     */
    public Date getLogoutTime() {
        return logoutTime;
    }

    /**
     * @param logoutTime
     */
    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

    /**
     * @return login_ip
     */
    public String getLoginIp() {
        return loginIp;
    }

    /**
     * @param loginIp
     */
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp == null ? null : loginIp.trim();
    }

    /**
     * @return login_city
     */
    public String getLoginCity() {
        return loginCity;
    }

    /**
     * @param loginCity
     */
    public void setLoginCity(String loginCity) {
        this.loginCity = loginCity == null ? null : loginCity.trim();
    }

    /**
     * @return login_province
     */
    public String getLoginProvince() {
        return loginProvince;
    }

    /**
     * @param loginProvince
     */
    public void setLoginProvince(String loginProvince) {
        this.loginProvince = loginProvince == null ? null : loginProvince.trim();
    }

    /**
     * @return login_country
     */
    public String getLoginCountry() {
        return loginCountry;
    }

    /**
     * @param loginCountry
     */
    public void setLoginCountry(String loginCountry) {
        this.loginCountry = loginCountry == null ? null : loginCountry.trim();
    }
}