package com.rainbow.exception;

import com.fasterxml.jackson.databind.ser.Serializers;

/**
 * 账户禁言失败异常
 */
public class AccountEnableFailedException extends BaseException {
    public AccountEnableFailedException() {

    }

    public AccountEnableFailedException(String msg) {
        super(msg);
    }
}
