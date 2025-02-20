package com.haifan.forum.exception;

import com.haifan.forum.common.AppResult;
import lombok.Data;
import org.apache.ibatis.annotations.Param;

/**
 * 自定义异常
 * @Author HaiFan
 */


@Data
public class ApplicationException extends RuntimeException{

    // 在一场中持有一个错误信息对象啊
    protected AppResult errorResult;

    public ApplicationException(AppResult errorResult) {
        super(errorResult.getMessage());
        this.errorResult = errorResult;
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, AppResult errorResult) {
        super(message);
        this.errorResult = errorResult;
    }

    public ApplicationException(String message, Throwable cause, AppResult errorResult) {
        super(message, cause);
        this.errorResult = errorResult;
    }

    public ApplicationException(Throwable cause, AppResult errorResult) {
        super(cause);
        this.errorResult = errorResult;
    }
}
