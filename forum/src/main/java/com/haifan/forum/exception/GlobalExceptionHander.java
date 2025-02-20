package com.haifan.forum.exception;

import com.haifan.forum.common.AppResult;
import com.haifan.forum.common.ResultCode;
import javafx.application.Application;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器
 * @Author HaiFan
 */

@ControllerAdvice
@Slf4j
public class GlobalExceptionHander {

    @ResponseBody
    @ExceptionHandler(ApplicationException.class)
    public AppResult applicationException (ApplicationException e) {
        // 打印异常信息
        e.printStackTrace();

        // 打印日志
        log.error(e.getMessage());
        // 获取异常信息
        if (e.getErrorResult() != null) {
            return e.getErrorResult();
        }

        return AppResult.failed(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public AppResult handleEception (Exception e) {
        e.printStackTrace();

        log.error(e.getMessage());

        if (e.getMessage() == null) {
            return AppResult.failed(ResultCode.ERROR_SERVICES);
        }

        return AppResult.failed(e.getMessage());
    }
}
