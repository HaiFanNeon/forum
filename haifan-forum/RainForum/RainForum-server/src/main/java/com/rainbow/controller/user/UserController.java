package com.rainbow.controller.user;



import cn.hutool.core.bean.BeanUtil;
import com.rainbow.dto.UserDTO;
import com.rainbow.entity.User;
import com.rainbow.query.UserQuery;
import com.rainbow.service.IUservice;
import com.rainbow.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
@Api(tags = "用户管理接口")
public class UserController {
    private final IUservice uservice;

    @PostMapping
    @ApiOperation("新增用户")
    public void saveUser(@RequestBody UserDTO userDTO) {
        User user = BeanUtil.copyProperties(userDTO, User.class);
        uservice.save(user);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除用户")
    public void removeUserById(@PathVariable("id") Long userId) {
        uservice.removeById(userId);
    }

    @GetMapping("/{id}")
    @ApiOperation("查询用户")
    public UserVO queryUserById(@PathVariable("id") Long userId) {
        User user = uservice.getById(userId);
        return BeanUtil.copyProperties(user, UserVO.class);
    }

    @GetMapping
    @ApiOperation("批量查询")
    public List<UserVO> queryUserByIds(@RequestParam("ids") List<Long> ids) {
        List<User> users = uservice.listByIds(ids);

        return BeanUtil.copyToList(users, UserVO.class);
    }

    @GetMapping("/list")
    @ApiOperation("根据id集合查询用户")
    public List<UserVO> queryUsers(UserQuery userQuery) {
        String username = userQuery.getName();
        String gender = userQuery.getGender();
        String state = userQuery.getState();

        List<User> list = uservice.lambdaQuery()
                .like(username != null, User::getUsername, username)
                .eq(state != null, User::getState, state)
                .eq(gender != null, User::getGender, gender)
                .list();
        return BeanUtil.copyToList(list, UserVO.class);
    }

    @PostMapping("/create")
    @ApiOperation("创建普通用户")
    public void createNormalUser(@RequestBody UserDTO userDTO) {
        uservice.createNormalUser(BeanUtil.copyProperties(userDTO, User.class));
    }
}
