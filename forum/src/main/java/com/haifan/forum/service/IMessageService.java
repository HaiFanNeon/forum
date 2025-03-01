package com.haifan.forum.service;

import com.haifan.forum.model.Message;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IMessageService {

    /**
     * 发送站内信息
     * @param message 站内信息对象
     */
    void create (Message message);

    /**
     * 根据用户id查询信息未读数量
     * @param receiveUesrId
     * @return
     */
    Integer selectUnreadCount (Long receiveUesrId);

    /**
     *  根据接收者id查询站内信息列表
     * @param receiveUserId
     * @return
     */
    List<Message> selectByReceiveUserId(Long receiveUserId);

    /**
     * 根据id更新站内信息的状态
     * @param id
     * @param state
     */
    void updateStateById(Long id, Byte state);

    /**
     * 根据id查询站内信
     * @param id
     * @return
     */
    Message selectById (Long id);

    /**
     * 回复站内信
     * @param repliedId
     * @param message
     */
    @Transactional
    void reply (Long repliedId, Message message);
}
