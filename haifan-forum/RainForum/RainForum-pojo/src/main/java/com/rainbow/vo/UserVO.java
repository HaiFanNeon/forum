package com.rainbow.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO implements Serializable {
    private String username;
    private String nickname;
    private Integer gender;
    private String remark;
    private Integer state;
    // Other fields that are relevant to business logic

    // Getters and Setters
}
