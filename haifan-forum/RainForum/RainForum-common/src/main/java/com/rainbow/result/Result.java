package com.rainbow.result;


import com.rainbow.constant.MessageConstant;
import lombok.Data;

import java.io.Serializable;

/***
 * 后端统一返回结果
 * @param <T>
 */

@Data
public class Result<T> implements Serializable {
    private Integer code; // 状态码, 1：成功，0和其他数字为失败
    private String msg; // 错误消息
    private T data; // 数据

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.code = 1;
        result.msg = MessageConstant.SUCCESS;
        return result;
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<>();
        result.code = 1;
        result.data = object;
        result.msg = MessageConstant.SUCCESS;
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.msg = msg;
        result.code = 0;
        return result;
    }
}
