package com.haifan.forum.controller;


import com.haifan.forum.common.AppResult;
import com.haifan.forum.common.ResultCode;
import com.haifan.forum.model.User;
import com.haifan.forum.service.IUserService;
import com.haifan.forum.utils.JWTUtil;
import com.haifan.forum.utils.MD5Util;
import com.haifan.forum.utils.StringUtil;
import com.haifan.forum.utils.UUIDUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequestMapping("/user")
@RestController
@Api(tags = "用户相关接口")
public class UserController {

    @Resource
    private IUserService userService;


    /**
     * @param username
     * @param nickname
     * @param password
     * @param passwordRepeat
     * @return
     */
    @ApiOperation("用户注册")
    @PostMapping("/register")
    public AppResult register (@ApiParam("username") @RequestParam("username") @NonNull String username,
                               @ApiParam("nickname") @RequestParam("nickname") @NonNull String nickname,
                               @ApiParam("password") @RequestParam("password") @NonNull String password,
                               @ApiParam("passwordRepeat") @RequestParam("passwordRepeat") @NonNull String passwordRepeat) {
//        if (StringUtil.isEmpty(username)
//                || StringUtil.isEmpty(nickname)
//                || StringUtil.isEmpty(password)
//                || StringUtil.isEmpty(passwordRepeat)) {
//            return AppResult.failed(ResultCode.FAILED_PARAMS_VALIDATE);
//        }

        if (!password.equals(passwordRepeat)) {
            log.warn(ResultCode.FAILED_TWO_PWD_NOT_SAME.getMessage());

            return AppResult.failed(ResultCode.FAILED_TWO_PWD_NOT_SAME);
        }

        User user = new User();
        user.setNickname(nickname);
        user.setUsername(username);

        String salt = UUIDUtil.UUID_32();
        String encryptPassword = MD5Util.md5Salt(password, salt);
        user.setPassword(encryptPassword);
        user.setSalt(salt);

        userService.createNormalUser(user);

        return AppResult.success();
    }

    @PostMapping("/login")
    @ApiOperation("用户登录")
    public AppResult login(@ApiParam("username") @RequestParam("username") @NonNull String username,
                           @ApiParam("password") @RequestParam("password") @NonNull String password,
                            HttpServletResponse response) {

        User user = userService.login(username, password);

        if (user == null) {
            log.warn(ResultCode.FAILED_USER_NOT_EXISTS.getMessage());
            return AppResult.failed(ResultCode.FAILED_USER_NOT_EXISTS);
        }

        Map<String, Object> map = new HashMap<>();
        log.info(user.getId().getClass().getName());
        map.put("id", user.getId());
        map.put("username", user.getUsername());

        String token = JWTUtil.getToken(map);

        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("user_token", token);

        return AppResult.success(tokenMap);
    }

    @GetMapping("/logout")
    @ApiOperation("/用户退出")
    public AppResult logout() {
        return AppResult.success();
    }


    @GetMapping("/info")
    @ApiOperation("根据id查询用户信息")
    public User getUserInfo(@ApiParam("id")
                                @RequestParam(value = "id", required = false) Long id,
                            HttpServletRequest request) {
        Long userId = 0L;
        if (id == null) {
            String userToken = request.getHeader("user_token");
            Claims claims = JWTUtil.parseToken(userToken);
            Integer i = (Integer) JWTUtil.getParam(claims, "id");
            userId = Long.valueOf(i);
        } else {
            userId = id;
        }

        return userService.selectById(userId);
    }


}
