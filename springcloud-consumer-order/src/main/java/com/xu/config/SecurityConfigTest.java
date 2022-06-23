package com.xu.config;

import com.xu.redis.JedisUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import redis.clients.jedis.Jedis;

@Configuration
//开启注解securedEnabled、prePostEnabled
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfigTest extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("开始验证");
        //自定义403页面
//        http.exceptionHandling().accessDeniedPage("/unauth");
        //自定义退出页面
        Jedis jedis = JedisUtil.getJedis();
        if (jedis.get("admin") != null || jedis.get("teacher") != null || jedis.get("student") != null) {
            System.out.println(jedis.get("admin"));
            System.out.println(jedis.get("teacher"));
            System.out.println(jedis.get("student"));
            System.out.println("进入");
            http.authorizeRequests().antMatchers("/**").permitAll();
        }
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/test/hello").permitAll();
        http.formLogin()        //自定义登录页面
                .loginPage("/login")                //登录页面
//                .loginProcessingUrl("/tologin")      //登录访问路径
                .permitAll()       //登录成功后，跳转的路径
                .and().authorizeRequests()
                .antMatchers("/login", "/register", "/tologin", "/toregister").permitAll()      //设置不需要认证路径
                .antMatchers("/static/**").permitAll()      //设置不需要认证路径
                .anyRequest().authenticated()           //全部需要认证
                .and().csrf().disable();                 //csrf防护关闭
        http.headers().frameOptions().disable();
    }


}
