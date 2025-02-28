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

    /**
     * 当前用户的发帖数 + 1
     * @param id 用户ID
     */
    void addOneArticleCountById(Long id);

    /**
     * 当前用户的发帖数 + 1
     * @param id
     */
    void subOneArticleCountById(Long id);

    /**
     * 修改个人信息
     * @param user
     */
    void modifyInfo (User user);

    /**
     * 修改密码
     * @param userId
     * @param oldPassword
     * @param newPassword
     */
    void modifyPassword(Long userId, String oldPassword, String newPassword);
}
