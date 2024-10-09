package com.rainbow.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户查询条件实体")
public class UserQuery {
    @ApiModelProperty("用户名关键字")
    private String name;

    @ApiModelProperty("性别 0 女 1 男 2 保密")
    private String gender;

    @ApiModelProperty("状态 1 禁言 0 正常")
    private String state;
}
