package com.rainbow.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rainbow.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("Update user set remark = #{remark} ${ew.customSqlSegment}")
    void deductBalanceByIds(@Param("remark") String remark,@Param("ew") QueryWrapper<User> wrapper);
}
