package com.rainbow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rainbow.entity.User;

public interface IUservice extends IService<User> {

    /**
     * 创建一个普通用户
     *
     * @param user 用户信息
     */
    void createNormalUser(User user);
}
