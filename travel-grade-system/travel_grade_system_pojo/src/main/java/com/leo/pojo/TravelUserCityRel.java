package com.leo.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

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
     * @return grade
     */
    public Double getGrade() {
        return grade;
    }

    /**
     * @param grade
     */
    public void setGrade(Double grade) {
        this.grade = grade;
    }

    /**
     * @return is_favour
     */
    public Boolean getIsFavour() {
        return isFavour;
    }

    /**
     * @param isFavour
     */
    public void setIsFavour(Boolean isFavour) {
        this.isFavour = isFavour;
    }

    /**
     * @return is_like
     */
    public Boolean getIsLike() {
        return isLike;
    }

    /**
     * @param isLike
     */
    public void setIsLike(Boolean isLike) {
        this.isLike = isLike;
    }
}