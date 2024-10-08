package com.rainbow.exception;

import com.fasterxml.jackson.databind.ser.Serializers;

/**
 * 账户禁言失败异常
 */
public class ApplicationException extends BaseException {
    public ApplicationException(String msg) {
        super(msg);
    }
    public ApplicationException() {

    }
}
