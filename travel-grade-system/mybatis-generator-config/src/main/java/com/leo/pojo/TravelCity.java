package com.leo.pojo;

import javax.persistence.*;

@Table(name = "leo_travel_system_city")
public class TravelCity {
    @Id
    private String id;

    private String name;

    @Column(name = "province_id")
    private String provinceId;

    @Column(name = "favour_count")
    private Integer favourCount;

    @Column(name = "commend_count")
    private Integer commendCount;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "grade_count")
    private Integer gradeCount;

    private Double grade;

    private String introduce;

    private String cover;

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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

    /**
     * @return commend_count
     */
    public Integer getCommendCount() {
        return commendCount;
    }

    /**
     * @param commendCount
     */
    public void setCommendCount(Integer commendCount) {
        this.commendCount = commendCount;
    }

    /**
     * @return like_count
     */
    public Integer getLikeCount() {
        return likeCount;
    }

    /**
     * @param likeCount
     */
    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
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
     * @return introduce
     */
    public String getIntroduce() {
        return introduce;
    }

    /**
     * @param introduce
     */
    public void setIntroduce(String introduce) {
        this.introduce = introduce == null ? null : introduce.trim();
    }

    /**
     * @return cover
     */
    public String getCover() {
        return cover;
    }

    /**
     * @param cover
     */
    public void setCover(String cover) {
        this.cover = cover == null ? null : cover.trim();
    }
}