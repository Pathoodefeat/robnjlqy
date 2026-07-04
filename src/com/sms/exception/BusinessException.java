package com.sms.exception;

/**
 * 自定义异常：业务校验异常（如非法输入、重复学号等）
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
