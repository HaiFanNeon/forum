package com.haifan.forum.service.impl;

import com.haifan.forum.dao.UserMapper;
import com.haifan.forum.model.User;
import com.haifan.forum.service.IUserService;
import com.haifan.forum.utils.MD5Util;
import com.haifan.forum.utils.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
class UserServiceImplTest {

    @Resource
    private IUserService userService;

    @Transactional
    @Test
    void createNormalUser() {
        User user = new User();
        user.setUsername("zhangsan");
        user.setNickname("zhangsan");

        String password = "123456";

        String salt = UUIDUtil.UUID_32();
        String ciphertext = MD5Util.md5Salt(password, salt);
        user.setSalt(salt);
        user.setPassword(ciphertext);

        userService.createNormalUser(user);

        log.info(user.getId().toString());
    }

    @Transactional
    @Test
    void selectByUserName() {
        User user = userService.selectByUserName("zhangsan");
        log.info(user.toString());
    }

    @Transactional
    @Test
    void login() {
        User zhangsan = userService.login("zhangsan", "123456");
        log.info(zhangsan.toString());
    }

    @Test
    void selectById() {
        User user = userService.selectById(1l);
        log.info(user.toString());
    }

    @Test
    @Transactional
    void addOneArticleCountById() {
        userService.addOneArticleCountById(1l);
    }
}