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
public class BoardVO implements Serializable {
    private String name;
    private Integer sort;
    private Integer state;
    // Other fields that are relevant to business logic

    // Getters and Setters
}