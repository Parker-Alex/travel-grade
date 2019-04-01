package com.leo.pojo;

import lombok.Data;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Date;

@Data
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
     * 根评论
     */
    @Column(name = "parent_comment_id")
    private String parentCommentId;

    @Column(name = "send_date")
    private Date sendDate;

}