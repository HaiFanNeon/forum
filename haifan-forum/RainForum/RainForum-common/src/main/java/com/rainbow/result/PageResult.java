package com.rainbow.result;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult implements Serializable {
    private long total; // 总记录数
    private List records; // 当前页数据集合
}
