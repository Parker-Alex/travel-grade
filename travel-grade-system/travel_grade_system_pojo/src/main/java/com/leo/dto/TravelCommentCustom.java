package com.leo.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
public class TravelCommentCustom {
    private String id;

    private String userId;

    private String cityId;

    private String content;

    private String toUserId;

    private Integer favourCount;

    /**
     * 根评论
     */
    private String parentCommentId;

    private Date sendDate;

    /**
     * 联表结果属性
     */
    private String nickName;

    private String avatar;

    private String toNickName;

    private String toAvatar;

}