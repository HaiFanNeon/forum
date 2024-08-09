package com.rainbow.dto;

import java.util.Date;

public class MessageDTO {
    private Long id;
    private Long postUserId;
    private Long receiveUserId;
    private String content;
    private Integer state; // 0: Normal, 1: Disabled
    private Integer deleteState; // 0: Not Deleted, 1: Deleted
    private Date createTime;
    private Date updateTime;

    // Getters and Setters
}