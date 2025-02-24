package com.haifan.forum.service.impl;


import com.haifan.forum.common.AppResult;
import com.haifan.forum.common.ResultCode;
import com.haifan.forum.dao.UserMapper;
import com.haifan.forum.exception.ApplicationException;
import com.haifan.forum.model.Board;
import com.haifan.forum.model.User;
import com.haifan.forum.service.IUserService;
import com.haifan.forum.utils.MD5Util;
import com.haifan.forum.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;


@Slf4j
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public void createNormalUser(User user) {
        if (user == null || StringUtil.isEmpty(user.getUsername())
                || StringUtil.isEmpty(user.getNickname())
                || StringUtil.isEmpty(user.getPassword())) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        User existsUser = userMapper.selectByUserName(user.getUsername());

        if (existsUser != null) {
            log.info(ResultCode.FAILED_USER_EXISTS.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_USER_EXISTS));
        }

        user.setGender((byte)2);
        user.setArticleCount(0);
        user.setIsAdmin((byte)0);
        user.setState((byte)0);
        user.setDeleteState((byte)0);

        Date date = new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);

        int row = userMapper.insertSelective(user);
        if (row != 1) {
            log.info(ResultCode.FAILED_CREATE.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_CREATE));
        }
        log.info("插入用户 ： " + ResultCode.SUCCESS.getMessage());
    }

    @Override
    public User selectByUserName(String username) {
        if (StringUtil.isEmpty(username)) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }
        return userMapper.selectByUserName(username);
    }

    @Override
    public User login(String username, String password) {

        if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
            log.warn(ResultCode.FAILED_LOGIN.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_LOGIN));
        }
        User user = selectByUserName(username);
        if (user == null) {
            log.warn(ResultCode.FAILED_LOGIN.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_LOGIN));
        }

        String finalPassword = MD5Util.md5Salt(password, user.getSalt());
        if (!user.getPassword().equalsIgnoreCase(finalPassword)) {
            log.warn(ResultCode.FAILED_LOGIN.getMessage() + " 密码错误, username = " + username);
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_LOGIN));
        }

        // success
        return user;
    }

    @Override
    public User selectById(Long id) {

        if (id == null) {
            log.warn(ResultCode.FAILED_NOT_EXISTS.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_NOT_EXISTS));
        }

        User user = userMapper.selectByPrimaryKey(id);

        if (user == null) {
            log.warn(ResultCode.FAILED_NOT_EXISTS.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_NOT_EXISTS));
        }

        return user;
    }

    @Override
    public void addOneArticleCountById(Long id) {
        if (id == null || id <= 0) {
            log.warn(ResultCode.FAILED_BOARD_ARTICLE_COUNT.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_BOARD_ARTICLE_COUNT));
        }

        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            log.warn(ResultCode.ERROR_IS_NULL.getMessage() + " : " + id.toString());
            throw new ApplicationException(AppResult.failed(ResultCode.ERROR_IS_NULL));
        }

        User updateUser = new User();
        updateUser.setId(user.getId());
        updateUser.setUpdateTime(new Date());
        updateUser.setArticleCount(user.getArticleCount() + 1);
        int i = userMapper.updateByPrimaryKeySelective(updateUser);
        if (i != 1) {
            log.warn(ResultCode.FAILED.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED));
        }
    }
}
