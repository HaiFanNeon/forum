package com.rainbow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.rainbow.annotation.AutoFill;
import com.rainbow.constant.MessageConstant;
import com.rainbow.entity.User;
import com.rainbow.enumeration.OperationType;
import com.rainbow.exception.ApplicationException;
import com.rainbow.exception.BaseException;
import com.rainbow.mapper.UserMapper;
import com.rainbow.result.Result;
import com.rainbow.service.IUservice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUservice {

    private final UserMapper userMapper;

    @Override
    @AutoFill(value = OperationType.INSERT)
    public void createNormalUser(User user) {
        // 非空校验
        if (user == null || StringUtil.isEmpty(user.getUsername()) || StringUtil.isEmpty(user.getPassword())) {
            log.info(MessageConstant.INFO_NOT_COMPLETE);
            throw new ApplicationException(MessageConstant.INFO_NOT_COMPLETE);
        }

        // 用户名是否已存在
        if (userMapper.selectOne(new QueryWrapper<User>().eq("username", user.getUsername())) != null) {
            log.info(MessageConstant.USER_EXISTS);
            throw new ApplicationException(MessageConstant.USER_EXISTS);
        }

        userMapper.insert(user);

        log.info("新增用户成功:{}", user.getUsername());
    }
}
