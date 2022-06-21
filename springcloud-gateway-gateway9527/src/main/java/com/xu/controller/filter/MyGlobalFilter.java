//package com.xu.controller.filter;
//
//import com.xu.controller.redis.JedisUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.server.RequestPath;
//import org.springframework.http.server.reactive.ServerHttpRequest;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//import reactor.netty.http.server.HttpServerRequest;
//import redis.clients.jedis.Jedis;
//
//import javax.servlet.http.HttpServletRequest;
//import java.time.ZonedDateTime;
//import java.util.Date;
//
//@Component
//@Slf4j
//public class MyGlobalFilter implements GlobalFilter, Ordered {
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        //访问路径
//        String path = exchange.getRequest().getPath().toString();
//        if(!path.contains("static"))
//        System.out.println(path);
//        //认证和授权功能
//       //以下请求可以直接访问
//        if(path.contains("static")||path.equals("/login")||path.equals("/register")||path.equals("/tologin")||path.equals("/toregister"))
//            return chain.filter(exchange);
//
//       //登录后可以访问
//        Jedis jedis = JedisUtil.getJedis();
//        if(path.contains("admin") && jedis.get("admin") != null|| path.contains("teacher") && jedis.get("teacher") != null ||path.contains("student") && jedis.get("student") != null)
//        return chain.filter(exchange);
//
////        if(exchange.getRequest().getCookies().get("id") != null)
////            return chain.filter(exchange);
//        //其他请求全部拒绝
//        exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
//        return exchange.getResponse().setComplete();
//    }
//
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}
