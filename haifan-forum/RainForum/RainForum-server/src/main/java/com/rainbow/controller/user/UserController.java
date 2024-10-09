package com.rainbow.controller.user;



import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.util.StringUtil;
import com.rainbow.constant.MessageConstant;
import com.rainbow.dto.UserDTO;
import com.rainbow.entity.User;
import com.rainbow.query.UserQuery;
import com.rainbow.result.Result;
import com.rainbow.service.IUservice;
import com.rainbow.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Api(tags = "用户管理接口")
@Slf4j
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
    public Result<UserVO> queryUserById(@PathVariable("id")Long id) {
        User user = uservice.getById(id);
        log.info(user.toString());
        UserVO userVO = BeanUtil.copyProperties(user, UserVO.class);
        return Result.success(userVO);
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

    @PostMapping("/register")
    @ApiOperation("注册用户")
    public Result<String> createNormalUser(@ApiParam("注册用户信息") @NonNull @RequestBody UserDTO userDTO) {
        User user = BeanUtil.copyProperties(userDTO, User.class);
        if (StringUtil.isEmpty(user.getUsername()) || StringUtil.isEmpty(user.getPassword())
        || StringUtil.isEmpty(user.getNickname())) {
            return Result.error(MessageConstant.INFO_NOT_COMPLETE);
        }
        uservice.createNormalUser(user);
        return Result.success(MessageConstant.SUCCESS);
    }
}
