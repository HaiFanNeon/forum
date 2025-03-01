package com.haifan.forum.controller;


import com.haifan.forum.common.AppResult;
import com.haifan.forum.common.ResultCode;
import com.haifan.forum.model.Message;
import com.haifan.forum.model.User;
import com.haifan.forum.service.IMessageService;
import com.haifan.forum.service.IUserService;
import com.haifan.forum.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/message")
@Api(tags = "站内信息接口")
public class MessageController {


    @Resource
    private IMessageService messageService;

    @Resource
    private IUserService userService;

    @ApiOperation("发送站内信息")
    @PostMapping("/send")
    public AppResult send(HttpServletRequest request,
                          @ApiParam("receiveUserId") @RequestParam("receiveUserId") @NonNull Long receiveUserId,
                          @ApiParam("content") @RequestParam("content") @NonNull String content) {

        String token = request.getHeader("user_token");
        if (token == null) token = request.getParameter("user_token");
        if (token == null) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user_token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        Claims claims = JWTUtil.parseToken(token);
        Integer id = (Integer) JWTUtil.getParam(claims, "id");
        Long userId = id.longValue();
        User sendUser = userService.selectById(userId);
        if (userId.equals(receiveUserId)) {
            return AppResult.failed("不能给自己发送站内信息");
        }

        if (sendUser.getState() == 1) {
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }

        User receiveUser = userService.selectById(receiveUserId);
        if (receiveUser == null || receiveUser.getDeleteState() == 1) {
            return AppResult.failed("接收者状态异常");
        }

        Message message = new Message();
        message.setPostUserId(userId);
        message.setReceiveUserId(receiveUserId);
        message.setContent(content);

        messageService.create(message);

        return AppResult.success();
    }


    @GetMapping("/getUnreadCount")
    @ApiOperation("获取当前用户未读信息的数量")
    public AppResult getUnreadCount(HttpServletRequest request) {
        String token = request.getHeader("user_token");
        if (token == null) token = request.getParameter("user_token");
        if (token == null) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user_token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        Claims claims = JWTUtil.parseToken(token);
        Integer id = (Integer) JWTUtil.getParam(claims, "id");
        Long userId = id.longValue();

        Integer count = messageService.selectUnreadCount(userId);
        return AppResult.success(count);
    }

    @ApiOperation("根据id查询出来站内信息列表")
    @GetMapping("/getAll")
    public AppResult<List<Message>> getAll (HttpServletRequest request) {

        String token = request.getHeader("user_token");
        if (token == null) token = request.getParameter("user_token");
        if (token == null) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user_token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        Claims claims = JWTUtil.parseToken(token);
        Integer id = (Integer) JWTUtil.getParam(claims, "id");
        Long userId = id.longValue();

        List<Message> messages = messageService.selectByReceiveUserId(userId);
        return AppResult.success(messages);
    }

    @ApiOperation("更新为已读")
    @PostMapping("/markRead")
    public AppResult markRead (@ApiParam("id") @RequestParam("id") @NonNull Long id, HttpServletRequest request) {

        Message message = messageService.selectById(id);
        if (message == null || message.getDeleteState() == 1) {
            return AppResult.failed(ResultCode.FAILED_MESSAGE_NOT_EXISTS);
        }

        String token = request.getHeader("user_token");
        if (token == null) token = request.getParameter("user_token");
        if (token == null) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user_token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        Claims claims = JWTUtil.parseToken(token);
        Integer id1 = (Integer) JWTUtil.getParam(claims, "id");
        Long userId = id1.longValue();

        if (userId.equals(id)) {
            return AppResult.failed(ResultCode.FAILED_FORBIDDEN);
        }

        messageService.updateStateById(id, (byte)ResultCode.READ.getCode());

        return AppResult.success();
    }

    @ApiOperation("站内信回复")
    @PostMapping("/reply")
    public AppResult reply (HttpServletRequest request,
                          @ApiParam("repliedId") @RequestParam("repliedId") @NonNull Long repliedId,
                          @ApiParam("content") @RequestParam("content") @NonNull String content) {
        Message message = messageService.selectById(repliedId);
        if (message == null || message.getDeleteState() == 1) {
            return AppResult.failed(ResultCode.FAILED_MESSAGE_NOT_EXISTS);
        }

        String token = request.getHeader("user_token");
        if (token == null) token = request.getParameter("user_token");
        if (token == null) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user_token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        Claims claims = JWTUtil.parseToken(token);
        Integer id = (Integer) JWTUtil.getParam(claims, "id");
        Long userId = id.longValue();
        User user = userService.selectById(userId);
        if (user.getState() == 1) {
            return AppResult.failed(ResultCode.FAILED_USER_BANNED);
        }
        if (userId.equals(repliedId)) {
            return AppResult.failed("不能给自己回复");
        }

        Message sendMessage = new Message();
        sendMessage.setPostUserId(userId);
        sendMessage.setReceiveUserId(message.getPostUserId());
        sendMessage.setContent(content);

        messageService.reply(repliedId, sendMessage);

        return AppResult.success();
    }
}
