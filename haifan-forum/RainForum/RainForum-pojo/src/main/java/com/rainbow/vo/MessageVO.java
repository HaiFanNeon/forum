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
public class MessageVO implements Serializable {
    private Long postUserId;
    private Long receiveUserId;
    private String content;
    private Integer state;
    // Other fields that are relevant to business logic

    // Getters and Setters
}