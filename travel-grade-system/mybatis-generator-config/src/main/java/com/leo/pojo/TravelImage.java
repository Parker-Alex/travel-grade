package com.leo.pojo;

import javax.persistence.*;

@Table(name = "leo_travel_system_image")
public class TravelImage {
    @Id
    private String id;

    @Column(name = "city_id")
    private String cityId;

    @Column(name = "user_id")
    private String userId;

    private String image1;

    private String image2;

    private String image3;

    private String image4;

    private String image5;

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
     * @return image1
     */
    public String getImage1() {
        return image1;
    }

    /**
     * @param image1
     */
    public void setImage1(String image1) {
        this.image1 = image1 == null ? null : image1.trim();
    }

    /**
     * @return image2
     */
    public String getImage2() {
        return image2;
    }

    /**
     * @param image2
     */
    public void setImage2(String image2) {
        this.image2 = image2 == null ? null : image2.trim();
    }

    /**
     * @return image3
     */
    public String getImage3() {
        return image3;
    }

    /**
     * @param image3
     */
    public void setImage3(String image3) {
        this.image3 = image3 == null ? null : image3.trim();
    }

    /**
     * @return image4
     */
    public String getImage4() {
        return image4;
    }

    /**
     * @param image4
     */
    public void setImage4(String image4) {
        this.image4 = image4 == null ? null : image4.trim();
    }

    /**
     * @return image5
     */
    public String getImage5() {
        return image5;
    }

    /**
     * @param image5
     */
    public void setImage5(String image5) {
        this.image5 = image5 == null ? null : image5.trim();
    }
}