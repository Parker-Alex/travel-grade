package com.leo.pojo;

import java.util.Date;
import javax.persistence.*;

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
    private String commendCount;

    @Column(name = "opend_id")
    private String opendId;

    private Integer level;

    private Boolean deleted;

    @Column(name = "add_time")
    private Date addTime;

    @Column(name = "updata_time")
    private Date updataTime;

    private String moblie;

    private Byte gender;

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
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * @return avatar
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * @param avatar
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    /**
     * @return title_name
     */
    public String getTitleName() {
        return titleName;
    }

    /**
     * @param titleName
     */
    public void setTitleName(String titleName) {
        this.titleName = titleName == null ? null : titleName.trim();
    }

    /**
     * @return follow_count
     */
    public Integer getFollowCount() {
        return followCount;
    }

    /**
     * @param followCount
     */
    public void setFollowCount(Integer followCount) {
        this.followCount = followCount;
    }

    /**
     * @return recommend_count
     */
    public Integer getRecommendCount() {
        return recommendCount;
    }

    /**
     * @param recommendCount
     */
    public void setRecommendCount(Integer recommendCount) {
        this.recommendCount = recommendCount;
    }

    /**
     * @return commend_count
     */
    public String getCommendCount() {
        return commendCount;
    }

    /**
     * @param commendCount
     */
    public void setCommendCount(String commendCount) {
        this.commendCount = commendCount == null ? null : commendCount.trim();
    }

    /**
     * @return opend_id
     */
    public String getOpendId() {
        return opendId;
    }

    /**
     * @param opendId
     */
    public void setOpendId(String opendId) {
        this.opendId = opendId == null ? null : opendId.trim();
    }

    /**
     * @return level
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * @param level
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * @return deleted
     */
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * @param deleted
     */
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * @return add_time
     */
    public Date getAddTime() {
        return addTime;
    }

    /**
     * @param addTime
     */
    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    /**
     * @return updata_time
     */
    public Date getUpdataTime() {
        return updataTime;
    }

    /**
     * @param updataTime
     */
    public void setUpdataTime(Date updataTime) {
        this.updataTime = updataTime;
    }

    /**
     * @return moblie
     */
    public String getMoblie() {
        return moblie;
    }

    /**
     * @param moblie
     */
    public void setMoblie(String moblie) {
        this.moblie = moblie == null ? null : moblie.trim();
    }

    /**
     * @return gender
     */
    public Byte getGender() {
        return gender;
    }

    /**
     * @param gender
     */
    public void setGender(Byte gender) {
        this.gender = gender;
    }
}