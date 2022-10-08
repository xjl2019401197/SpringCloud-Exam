package com.xu.handle;

import lombok.NoArgsConstructor;

/**
 * 统一异常类
 * @ClassName GlobalException
 * @Author YH
 * @Date 2021/11/11
 * @Version 1.0
 */
@NoArgsConstructor
public class GlobalException extends RuntimeException {

    static final Long serialVersionUID = 1L;

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(Throwable throwable) {
        super(throwable);
    }

    public GlobalException(String msg, Throwable cause,
                           boolean enableSuppression,
                           boolean writableStackTrace) {
        super(msg, cause, enableSuppression, writableStackTrace);
    }
}
