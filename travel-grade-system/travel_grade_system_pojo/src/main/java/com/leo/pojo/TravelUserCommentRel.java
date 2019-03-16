package com.leo.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "leo_travel_system_user_comment_rel")
public class TravelUserCommentRel {
    @Id
    private String id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "favour_comment_id")
    private String favourCommentId;

    @Column(name = "favour_date")
    private Date favourDate;

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
     * @return favour_comment_id
     */
    public String getFavourCommentId() {
        return favourCommentId;
    }

    /**
     * @param favourCommentId
     */
    public void setFavourCommentId(String favourCommentId) {
        this.favourCommentId = favourCommentId == null ? null : favourCommentId.trim();
    }

    /**
     * @return favour_date
     */
    public Date getFavourDate() {
        return favourDate;
    }

    /**
     * @param favourDate
     */
    public void setFavourDate(Date favourDate) {
        this.favourDate = favourDate;
    }
}