package com.wang.exception;

/**
 * 异常的提示信息类
 */
public class SysException extends Exception {
    public String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SysException(String message) {
        this.message = message;
    }
}
