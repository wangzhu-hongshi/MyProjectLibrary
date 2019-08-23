package com.wang.controller.exception;

/**
 * 自定义异常类 需要继承 Exception
 */
public class SysException extends Exception {
     public String message;

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
