package com.rainbow.mapper;

import com.rainbow.entity.Message;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Rainbow-Su
 * @since 2024-08-23
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}
