package com.rainbow.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rainbow.entity.User;
import org.apache.ibatis.annotations.Mapper;
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
