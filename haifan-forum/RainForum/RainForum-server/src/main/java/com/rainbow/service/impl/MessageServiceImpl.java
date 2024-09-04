package com.rainbow.service.impl;

import com.rainbow.entity.Message;
import com.rainbow.mapper.MessageMapper;
import com.rainbow.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Rainbow-Su
 * @since 2024-08-23
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}
