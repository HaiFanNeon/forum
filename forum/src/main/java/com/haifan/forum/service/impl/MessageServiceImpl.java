package com.haifan.forum.service.impl;

import com.haifan.forum.common.AppResult;
import com.haifan.forum.common.ResultCode;
import com.haifan.forum.dao.MessageMapper;
import com.haifan.forum.exception.ApplicationException;
import com.haifan.forum.model.Message;
import com.haifan.forum.model.User;
import com.haifan.forum.service.IMessageService;
import com.haifan.forum.service.IUserService;
import com.haifan.forum.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class MessageServiceImpl implements IMessageService {

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private IUserService userService;

    @Override
    public void create(Message message) {
        if (message == null || message.getPostUserId() == null || message.getReceiveUserId() == null || StringUtil.isEmpty(message.getContent())) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.getMessage());

            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        User user = userService.selectById(message.getReceiveUserId());
        if (user == null || user.getDeleteState() == 1) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        message.setState((byte) 0);
        message.setDeleteState((byte) 0);

        Date date = new Date();
        message.setCreateTime(date);
        message.setUpdateTime(date);

        int i = messageMapper.insertSelective(message);
        if (i != 1) {
            log.warn(ResultCode.FAILED_CREATE.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_CREATE));
        }


    }

    @Override
    public Integer selectUnreadCount(Long receiveUserId) {
        if (receiveUserId == null || receiveUserId <= 0) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.getMessage());

            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        Integer count = messageMapper.selectUnreadCount(receiveUserId);
        if (count == null) {
            log.warn(ResultCode.ERROR_SERVICES.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }

        return count;
    }

    @Override
    public List<Message> selectByReceiveUserId(Long receiveUserId) {
        if (receiveUserId == null || receiveUserId <= 0) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.getMessage());

            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        return messageMapper.selectByReceiveUserId(receiveUserId);
    }

    @Override
    public void updateStateById(Long id, Byte state) {
        if (id == null || id <= 0 || state < ResultCode.UNREAD.getCode() || state > ResultCode.RECOVERED.getCode()) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.getMessage());

            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        Message updateMessage = new Message();
        updateMessage.setId(id);
        updateMessage.setState(state);

        Date date = new Date();
        updateMessage.setUpdateTime(date);

        int i = messageMapper.updateByPrimaryKeySelective(updateMessage);
        if (i != 1) {
            log.warn(ResultCode.ERROR_SERVICES.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_SERVICES));
        }

    }

    @Override
    public Message selectById (Long id) {
        if (id == null || id <= 0) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.getMessage());

            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        return messageMapper.selectByPrimaryKey(id);
    }

    @Override
    public void reply (Long repliedId, Message message) {
        if (repliedId == null || repliedId <= 0) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        Message existsMessage = messageMapper.selectByPrimaryKey(repliedId);
        if (existsMessage == null || existsMessage.getDeleteState() == 1) {
            log.warn(ResultCode.FAILED_MESSAGE_NOT_EXISTS.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_MESSAGE_NOT_EXISTS));
        }
        updateStateById(repliedId, (byte)ResultCode.READ.getCode());
        create(message);
    }
}
