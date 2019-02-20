package com.leo.pojo;

import javax.persistence.*;

@Table(name = "leo_travel_system_recommend")
public class TravelRecommend {
    @Id
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "city_image")
    private String cityImage;

    private String reason;

    @Column(name = "province_id")
    private String provinceId;

    private Double grade;

    @Column(name = "grade_count")
    private Integer gradeCount;

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
     * @return city_name
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param cityName
     */
    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    /**
     * @return city_image
     */
    public String getCityImage() {
        return cityImage;
    }

    /**
     * @param cityImage
     */
    public void setCityImage(String cityImage) {
        this.cityImage = cityImage == null ? null : cityImage.trim();
    }

    /**
     * @return reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    /**
     * @return province_id
     */
    public String getProvinceId() {
        return provinceId;
    }

    /**
     * @param provinceId
     */
    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId == null ? null : provinceId.trim();
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
     * @return grade_count
     */
    public Integer getGradeCount() {
        return gradeCount;
    }

    /**
     * @param gradeCount
     */
    public void setGradeCount(Integer gradeCount) {
        this.gradeCount = gradeCount;
    }
}