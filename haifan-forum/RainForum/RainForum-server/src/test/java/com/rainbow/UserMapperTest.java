package com.rainbow;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rainbow.entity.User;
import com.rainbow.mapper.UserMapper;
import com.rainbow.service.IUservice;
import com.rainbow.service.impl.UserServiceImpl;
import com.rainbow.utils.MD5Util;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RequiredArgsConstructor

public class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IUservice userService;

    @Test
    void testInsert() {
        User user = new User();
        user.setId(1000L); // 假设id为1
        user.setUsername("exampleUser123123"); // 设置用户名
        user.setPassword(MD5Util.md5("encryptedPassword123")); // 设置加密后的密码
        user.setNickname("ExampleNickname"); // 设置昵称
        user.setPhonenum("1234567890"); // 设置手机号
        user.setEmail("user@example.com"); // 设置电子邮箱
        user.setGender(1); // 假设性别为男
        user.setSalt("encryptionSalt"); // 设置密码盐
        user.setAvatarurl("/path/to/avatar.jpg"); // 设置用户头像路径
        user.setArticlecount(10); // 假设发帖数量为10
        user.setIsadmin(0); // 假设不是管理员
        user.setRemark("这是一个备注信息。"); // 设置备注
        user.setState(0); // 假设状态为正常
        user.setDeletestate(0); // 假设未被删除
        user.setCreatetime(new Date()); // 设置创建时间为当前时间
        user.setUpdatetime(new Date()); // 设置更新时间为当前时间

        userMapper.insert(user);
    }

    // 单个查询
    @Test
    void testSelectById() {
        User user = userMapper.selectById(2L);
        System.out.println("user = " + user);
    }
//    user = User(id=2, username=user1, password=encryptedPassword1, nickname=Nickname1, phonenum=12345678901, email=user1@example.com, gender=1, salt=encryptionSalt1, avatarurl=/path/to/avatar1.jpg, articlecount=5, isadmin=0, remark=Remark for user 1, state=0, deletestate=0, createtime=Mon Aug 12 00:00:00 CST 2024, updatetime=Mon Aug 12 00:00:00 CST 2024)



    @Test
    void createNormalUser() {
        User user = new User();
        user.setId(1L); // 假设id为1
        user.setUsername("exampleUser"); // 设置用户名
        user.setPassword(MD5Util.md5("encryptedPassword123")); // 设置加密后的密码
        user.setNickname("ExampleNickname"); // 设置昵称
        user.setPhonenum("1234567890"); // 设置手机号
        user.setEmail("user@example.com"); // 设置电子邮箱
        user.setGender(1); // 假设性别为男
        user.setSalt("encryptionSalt"); // 设置密码盐
        user.setAvatarurl("/path/to/avatar.jpg"); // 设置用户头像路径
        user.setArticlecount(10); // 假设发帖数量为10
        user.setIsadmin(0); // 假设不是管理员
        user.setRemark("这是一个备注信息。"); // 设置备注
        user.setState(0); // 假设状态为正常
        user.setDeletestate(0); // 假设未被删除


        userService.createNormalUser(user);
    }


    // 批量查询
    @Test
    void testSelectByIds() {
        List<User> userList = userMapper.selectBatchIds(Arrays.asList(2L, 3L, 4L));
        userList.forEach(System.out::println);
    }

//    User(id=2, username=user1, password=encryptedPassword1, nickname=Nickname1, phonenum=12345678901, email=user1@example.com, gender=1, salt=encryptionSalt1, avatarurl=/path/to/avatar1.jpg, articlecount=5, isadmin=0, remark=Remark for user 1, state=0, deletestate=0, createtime=Mon Aug 12 00:00:00 CST 2024, updatetime=Mon Aug 12 00:00:00 CST 2024)
//    User(id=3, username=user2, password=encryptedPassword2, nickname=Nickname2, phonenum=12345678902, email=user2@example.com, gender=2, salt=encryptionSalt2, avatarurl=/path/to/avatar2.jpg, articlecount=3, isadmin=1, remark=Remark for user 2, state=0, deletestate=0, createtime=Mon Aug 12 00:00:01 CST 2024, updatetime=Mon Aug 12 00:00:01 CST 2024)
//    User(id=4, username=user3, password=encryptedPassword3, nickname=Nickname3, phonenum=12345678903, email=user3@example.com, gender=0, salt=encryptionSalt3, avatarurl=/path/to/avatar3.jpg, articlecount=10, isadmin=1, remark=Remark for user 3, state=1, deletestate=0, createtime=Mon Aug 12 00:00:02 CST 2024, updatetime=Mon Aug 12 00:00:02 CST 2024)

    // 更新
    @Test
    void testUpdateById() {
        User user = new User();
        user.setId(2L);
        user.setUsername("hahahha");

        userMapper.updateById(user);
    }

    // 删除
    @Test
    void testDelete() {
        userMapper.deleteById(5L);
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .select("id","username", "remark")
                .like("username", "user")
                .ge("id", 3);

        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);
    }

    @Test
    void testUpdateByQueryWrapper() {
        QueryWrapper<User> wrapper = new QueryWrapper<User>()
                .eq("username", "user2");
        User user = new User();
        user.setRemark("hello world");
        userMapper.update(user, wrapper);
    }

    @Test
    void testUpdateWrapper() {
        List<Long> ids = Arrays.asList(1L, 4L);

        UpdateWrapper<User> wrapper = new UpdateWrapper<User>()
                .setSql("remark = \"UpdateWrapper\"")
                .in("id", ids);
        // 第一个参数可以给null，也就不填更新字段和数据，而是基于UpdateWrapper中的setSQL来更新
        userMapper.update(null, wrapper);
    }

    @Test
    void testLambdaQueryWrapper() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .select(User::getId, User::getUsername)
                .like(User::getUsername, "user");
        List<User> users = userMapper.selectList(wrapper);
        users.forEach(System.out::println);

    }

    @Test
    void testCustomWrapper() {
        // 1.准备自定义查询条件
        List<Long> ids = Arrays.asList(1L, 2L, 4L);
        QueryWrapper<User> wrapper = new QueryWrapper<User>().in("id", ids);

        // 2.调用mapper的自定义方法，直接传递Wrapper
        userMapper.deductBalanceByIds("hello java", wrapper);
    }
}
