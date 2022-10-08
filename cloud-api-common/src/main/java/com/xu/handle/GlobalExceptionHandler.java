package com.xu.handle;

import com.xu.pojo.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.RejectedExecutionException;

/**
 * @ClassName GlobalExceptionHandler
 * @Author YH
 * @Date 2021/11/17
 * @Version 1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理 Validated 校验异常
     */
    @ExceptionHandler(BindException.class)
    public R error(BindException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();

        return new R(false,e.getMessage());
    }

    /**
     * 处理 Validated 校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R error(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError().getDefaultMessage();

        return new R(false,e.getMessage());
    }

    /**
     * 自定义异常处理
     * 这个执行了，全局异常处理就不会执行了
     * 因为这个异常类是我们自己定义的，系统不会帮我们捕获，
     * 所以需要我们自己捕获异常。
     * @param e
     * @return
     */
    @ExceptionHandler(GlobalException.class)
    public R error(GlobalException e) {
        log.error(e.getMessage());
        return new R(false,e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public R error(RuntimeException e, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return new R(false,"发生未知异常"+" "+e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public R error(Exception e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
        return new R(false,"发生系统异常" +" " +e.getMessage());
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public R handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
                                                          HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
        return new R(false,e.getMessage());
    }

    /**
     * 捕捉线程池拒绝 RejectedExecution 异常
     * 如果捕捉到了发送消息给相应的开发人员
     * @return
     */
    @ExceptionHandler(RejectedExecutionException.class)
    public R handlerRejectedExecution() {
        return new R(false,"RejectedExecution 异常");
    }
}
