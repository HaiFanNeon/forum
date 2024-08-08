package com.rainbow.handler;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.rainbow.constant.MessageConstant;
import com.rainbow.exception.BaseException;
import com.rainbow.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler(BaseException.class)
    public Result exceptionHandler(BaseException ex) {
        log.error("异常信息:{}", ex.getMessage());
        if (StringUtils.isEmpty(ex.getMessage())) {
            return Result.error(MessageConstant.ERROR_SERVICES);
        }
        return Result.error(ex.getMessage());
    }

    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex) {
        // Duplicate entry 'zhangliu' for key 'idx_username' 用户名重复的异常信息
        String message = ex.getMessage();
        if (message.contains("Duplicate entry")) {
            String[] split = message.split(" ");
            String username = split[2];
            String msg = username + MessageConstant.USER_EXISTS;
            return Result.success(msg);
        } else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }

    }
}
