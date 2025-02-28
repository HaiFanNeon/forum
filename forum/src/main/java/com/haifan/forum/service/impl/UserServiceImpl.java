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
import com.haifan.forum.utils.UUIDUtil;
import com.sun.org.apache.xml.internal.security.encryption.EncryptedType;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    @Override
    public void subOneArticleCountById(Long id) {
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
        updateUser.setArticleCount(Math.max((user.getArticleCount() - 1), 0));

        int i = userMapper.updateByPrimaryKeySelective(updateUser);
        if (i != 1) {
            log.warn(ResultCode.FAILED.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED));
        }
    }

    @Override
    public void modifyInfo(User user) {
        if (user == null || user.getId() == null || user.getId() <= 0 ) {
            log.warn(ResultCode.FAILED_BOARD_ARTICLE_COUNT.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_BOARD_ARTICLE_COUNT));
        }

        User existsUser = userMapper.selectByPrimaryKey(user.getId());
        if (existsUser == null) {
            log.warn(ResultCode.FAILED_USER_NOT_EXISTS.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_USER_NOT_EXISTS));
        }
        boolean checkAttr = false;

        User updateUser = new User();
        updateUser.setId(user.getId());

        if (!StringUtil.isEmpty(user.getUsername()) &&
         !existsUser.getUsername().equals(user.getUsername())) {
            User checkUser = userMapper.selectByUserName(user.getUsername());
            if (checkUser != null) {
                log.warn(ResultCode.FAILED_USER_NOT_EXISTS.getMessage());
                throw  new ApplicationException(AppResult.failed(ResultCode.FAILED_USER_NOT_EXISTS));
            }

            updateUser.setUsername(user.getUsername());
            checkAttr = true;
        }

        if (!StringUtil.isEmpty(user.getNickname()) && !existsUser.getNickname().equals(user.getNickname())) {
            updateUser.setNickname(user.getNickname());
            checkAttr = true;
        }

        if (user.getGender() != null && existsUser.getGender() != user.getGender()) {
            updateUser.setGender(user.getGender());
            checkAttr = true;
        }

        if (!StringUtil.isEmpty(user.getEmail()) && !existsUser.getEmail().equals(user.getEmail())) {
            updateUser.setEmail(user.getEmail());
            checkAttr = true;
        }

        if (!StringUtil.isEmpty(user.getPhoneNum()) && !existsUser.getPhoneNum().equals(user.getPhoneNum())) {
            updateUser.setPhoneNum(user.getPhoneNum());
            checkAttr = true;
        }

        if (!StringUtil.isEmpty(user.getRemark()) && !existsUser.getRemark().equals(user.getRemark())) {
            updateUser.setRemark(user.getRemark());
            checkAttr = true;
        }

        if (!checkAttr) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        int i = userMapper.updateByPrimaryKeySelective(updateUser);
        if (i != 1) {
            log.warn(ResultCode.FAILED.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED));
        }
    }

    @Override
    public void modifyPassword(Long userId, String oldPassword, String newPassword) {
        if (userId == null || userId <= 0 || StringUtil.isEmpty(oldPassword) || StringUtil.isEmpty(newPassword)) {
            log.warn(ResultCode.FAILED_PARAMS_VALIDATE.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE));
        }

        User user = userMapper.selectByPrimaryKey(userId);
        if (user == null || user.getDeleteState() == 1) {
            log.warn(ResultCode.FAILED_USER_NOT_EXISTS.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED_USER_NOT_EXISTS));
        }

        String oldEncryptPassword = MD5Util.md5Salt(oldPassword, user.getSalt());

        if (!oldEncryptPassword.equalsIgnoreCase(user.getPassword())) {
            log.warn(ResultCode.FAILED.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED));
        }

        String salt = UUIDUtil.UUID_32();
        String encryptPassword = MD5Util.md5Salt(newPassword, salt);
        User updateUser = new User();
        updateUser.setId(userId);
        updateUser.setSalt(salt);
        updateUser.setPassword(encryptPassword);

        int i = userMapper.updateByPrimaryKeySelective(updateUser);
        if (i != 1) {
            log.warn(ResultCode.FAILED.getMessage());
            throw new ApplicationException(AppResult.failed(ResultCode.FAILED));
        }
    }


}
