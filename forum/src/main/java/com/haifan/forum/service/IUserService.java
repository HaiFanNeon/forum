package com.haifan.forum.service;

import com.haifan.forum.model.User;

/**
 * 用户接口
 *
 * @Author HaiFan
 */
public interface IUserService {

    /**
     * create user
     *
     * @param user create normal user
     */
    void createNormalUser (User user);

    /**
     * 根据用户名查询信息
     * @param username
     * @return 用户信息
     */
    User selectByUserName (String username);


    /**
     * 登录
     * @param username
     * @param password
     * @return 用户信息
     */
    User login (String username, String password);

    /**
     * @param id 用户id
     * @return 返回要查询的用户信息
     */
    User selectById(Long id);
}
