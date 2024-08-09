package com.rainbow.dto;

import java.util.Date;

public class ArticleDTO {
    private Long id;
    private Long boardId;
    private Long userId;
    private String title;
    private String content;
    private Integer visitCount;
    private Integer replyCount;
    private Integer likeCount;
    private Integer state; // 0: Normal, 1: Disabled
    private Integer deleteState; // 0: Not Deleted, 1: Deleted
    private Date createTime;
    private Date updateTime;

    // Getters and Setters
}