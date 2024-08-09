package com.rainbow.dto;

import java.util.Date;

public class BoardDTO {
    private Long id;
    private String name;
    private Integer articleCount;
    private Integer sort;
    private Integer state; // 0: Normal, 1: Disabled
    private Integer deleteState; // 0: Not Deleted, 1: Deleted
    private Date createTime;
    private Date updateTime;

    // Getters and Setters
}