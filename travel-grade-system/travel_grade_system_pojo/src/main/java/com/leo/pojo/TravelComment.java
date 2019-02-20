package com.leo.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "leo_travel_system_comment")
public class TravelComment {
    @Id
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "city_id")
    private String cityId;

    private String content;

    @Column(name = "to_user_id")
    private String toUserId;

    @Column(name = "favour_count")
    private Integer favourCount;

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
     * @return city_id
     */
    public String getCityId() {
        return cityId;
    }

    /**
     * @param cityId
     */
    public void setCityId(String cityId) {
        this.cityId = cityId == null ? null : cityId.trim();
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * @return to_user_id
     */
    public String getToUserId() {
        return toUserId;
    }

    /**
     * @param toUserId
     */
    public void setToUserId(String toUserId) {
        this.toUserId = toUserId == null ? null : toUserId.trim();
    }

    /**
     * @return favour_count
     */
    public Integer getFavourCount() {
        return favourCount;
    }

    /**
     * @param favourCount
     */
    public void setFavourCount(Integer favourCount) {
        this.favourCount = favourCount;
    }
}