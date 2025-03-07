package com.haifan.forum.common;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class AppResult<T> {

    @JsonInclude(JsonInclude.Include.ALWAYS) // 不论什么情况，都参与json序列化
    private int code;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private String message;

    @JsonInclude(JsonInclude.Include.ALWAYS)
    private T data;

    /**
     * 成功
     * @param code
     * @param message
     * @param data
     */
    public AppResult(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public AppResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> AppResult<T> success() {
        return new AppResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    public static <T> AppResult<T> success(String msg) {
        return new AppResult<T>(ResultCode.SUCCESS.getCode(), msg);
    }

    public static <T> AppResult<T> success(T data) {
        return new AppResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> AppResult<T> success(String msg, T data) {
        return new AppResult<T>(ResultCode.SUCCESS.getCode(), msg, data);
    }




    /**
     * 失败
     * @Return
     */

    public static <T> AppResult<T> failed() {
        return new AppResult<T>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage());
    }

    public static <T> AppResult<T> failed(String msg) {
        return new AppResult<T>(ResultCode.FAILED.getCode(), msg);
    }

    public static <T> AppResult<T> failed(String msg, T data) {
        return new AppResult<T>(ResultCode.FAILED.getCode(), msg, data);
    }

    public static <T> AppResult<T> failed(T data) {
        return new AppResult<T>(ResultCode.FAILED.getCode(), ResultCode.FAILED.getMessage(), data);
    }

}
