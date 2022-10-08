//package com.xu.handle;
//
//import com.alibaba.fastjson.JSON;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.Serializable;
//
///**
// * 认证失败处理类
// * @ClassName AuthenticationEntryPointImpl
// * @Author YH
// * @Date 2021/12/27
// * @Version 1.0
// */
//@Slf4j
//@Component
//public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * 认证失败要处理的逻辑
//     */
//    @Override
//    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
//            throws IOException {
//        request.setCharacterEncoding("utf-8");
//        response.setCharacterEncoding("utf-8");
//        String msg = "请求访问：{" + request.getRequestURI() + "}，认证失败，无法访问系统资源";
//        PrintWriter writer = response.getWriter();
//        response.setStatus(200);
//        String result = JSON.toJSONString(Result.failure(msg));
//        log.info(result);
//        response.setContentType("application/json;charset=UTF-8");
//        writer.println(result);
//        writer.close();
//    }
//}
