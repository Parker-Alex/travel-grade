package com.leo.pojo;

import javax.persistence.*;

@Table(name = "leo_travel_system_province")
public class TravelProvince {
    @Id
    private String id;

    private String name;

    private Double grade;

    @Column(name = "city_count")
    private Integer cityCount;

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
     * @return city_count
     */
    public Integer getCityCount() {
        return cityCount;
    }

    /**
     * @param cityCount
     */
    public void setCityCount(Integer cityCount) {
        this.cityCount = cityCount;
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