package com.leo.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "leo_travel_system_user_rel")
public class TravelUserRel {
    @Id
    private String id;

    @Column(name = "user_id")
    private String userId;

    /**
     * 关注的用户id
     */
    @Column(name = "attention_user_id")
    private String attentionUserId;

    /**
     * 关注日期
     */
    @Column(name = "attention_date")
    private Date attentionDate;

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
     * 获取关注的用户id
     *
     * @return attention_user_id - 关注的用户id
     */
    public String getAttentionUserId() {
        return attentionUserId;
    }

    /**
     * 设置关注的用户id
     *
     * @param attentionUserId 关注的用户id
     */
    public void setAttentionUserId(String attentionUserId) {
        this.attentionUserId = attentionUserId == null ? null : attentionUserId.trim();
    }

    /**
     * 获取关注日期
     *
     * @return attention_date - 关注日期
     */
    public Date getAttentionDate() {
        return attentionDate;
    }

    /**
     * 设置关注日期
     *
     * @param attentionDate 关注日期
     */
    public void setAttentionDate(Date attentionDate) {
        this.attentionDate = attentionDate;
    }
}