package com.haifan.forum.common;


import lombok.Data;

public enum ResultCode {

    SUCCESS                                 (0, "成功"),
    UNREAD                                  (1, "未读"),
    READ                                    (2, "已读"),
    RECOVERED                               (3, "已回复"),
    FAILED                                  (1000, "操作失败"),
    FAILED_UNAUTHORIZED                     (1001,"未授权"),
    FAILED_PARAMS_VALIDATE                  (1002,"参数校验失败"),
    FAILED_FORBIDDEN                        (1003,"禁止访问"),
    FAILED_CREATE                           (1004,"新增失败"),
    FAILED_NOT_EXISTS                       (1005,"资源不存在"),
    FAILED_USER_EXISTS                      (1101,"用户已存在"),
    FAILED_USER_NOT_EXISTS                  (1102,"用户不存在"),
    FAILED_LOGIN                            (1103,"用户名或密码错误"),
    FAILED_USER_BANNED                      (1104,"您已被禁言, 请联系管理员, 并重新登录."),
    FAILED_TWO_PWD_NOT_SAME                 (1105,"两次输入的密码不一致"),
    FAILED_PARSE_TOKEN                      (1201, "解析token失败"),
    FAILED_TOKEN_EXPIRED                    (1202, "token 已过期"),
    FAILED_TOKEN_NULL                       (1203, "token为空"),
    FAILED_BOARD_ARTICLE_COUNT              (1301, "更新帖子失败"),
    FAILED_BOARD_BANNED                     (1302, "板块状态异常"),
    FAILED_BOARD_NOT_EXISTS                 (1303, "板块不存在"),
    FAILED_ARTICLE_NOT_EXISTS               (1401, "帖子不存在"),
    FAILED_ARTICLE_BANNED                   (1402, "帖子状态异常"),
    FAILED_MESSAGE_NOT_EXISTS               (1501, "站内信不存在"),
    ERROR_SERVICES                          (2000,"服务器内部错误"),
    ERROR_IS_NULL                           (2001,"IS NULL.")
    ;

    // 状态码
    int code;

    // 描述信息
    String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResultCode{" + "code=" + code + ", message='" + message + '\'' + '}';
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
