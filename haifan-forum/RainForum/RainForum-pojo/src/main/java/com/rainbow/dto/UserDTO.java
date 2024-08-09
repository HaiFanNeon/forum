package com.rainbow.dto;

import java.util.Date;

public class UserDTO {
    private Long id;
    private String username;
    private String nickname;
    private String phoneNum;
    private String email;
    private Integer gender; // 0: Female, 1: Male, 2: Secret
    private String avatarUrl;
    private Integer articleCount;
    private Integer isAdmin; // 0: No, 1: Yes
    private String remark;
    private Integer state; // 0: Normal, 1: Muted
    private Integer deleteState; // 0: Not Deleted, 1: Deleted
    private Date createTime;
    private Date updateTime;

    // Getters and Setters
}