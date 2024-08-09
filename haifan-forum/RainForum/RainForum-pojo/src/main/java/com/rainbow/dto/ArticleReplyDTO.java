package com.rainbow.dto;

import java.util.Date;

public class ArticleReplyDTO {
    private Long id;
    private Long articleId;
    private Long postUserId;
    private Long replyId; // For nested replies
    private Long replyUserId; // For nested replies
    private String content;
    private Integer likeCount;
    private Integer state; // 0: Normal, 1: Disabled
    private Integer deleteState; // 0: Not Deleted, 1: Deleted
    private Date createTime;
    private Date updateTime;

    // Getters and Setters
}