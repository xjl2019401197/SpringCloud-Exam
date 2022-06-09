//package com.xu.controller.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
//import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
//
//import javax.sql.DataSource;
//
//@Configuration
////开启注解securedEnabled、prePostEnabled
//@EnableGlobalMethodSecurity(securedEnabled=true,prePostEnabled = true)
//public class SecurityConfigTest extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    //注入数据源
//    @Autowired
//    private DataSource dataSource;
//
//    //配置对象
//    @Bean
//    public PersistentTokenRepository persistentTokenRepository(){
//        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
//        jdbcTokenRepository.setDataSource(dataSource);
//        jdbcTokenRepository.setCreateTableOnStartup(false);    //自动生成表
//        return jdbcTokenRepository;
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(password());
//    }
//    @Bean
//    PasswordEncoder password(){
//        return new BCryptPasswordEncoder();
//    }
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        //自定义403页面
//        http.exceptionHandling().accessDeniedPage("/unauth.html");
//        //自定义退出页面
//        http.logout().logoutUrl("/logout").logoutSuccessUrl("/test/hello").permitAll();
//        http.formLogin()        //自定义登录页面
//                .loginPage("/login.html")                //登录页面
//                .loginProcessingUrl("/user/login")      //登录访问路径
//                .defaultSuccessUrl("/success.html").permitAll()       //登录成功后，跳转的路径
//                .and().authorizeRequests()
//                .antMatchers("/","/test/hello","/user/login").permitAll()      //设置不需要认证路径
//                //当前登录用户，只有具有admin权限才可以访问这个路径
//                //1、hasAnyAuthority方法
//                .antMatchers("/test/index").hasAnyAuthority("admins")
//                //2、hasAnyAuthority多个权限'0
////                        .antMatchers("/test/index").hasAnyAuthority("admins,manager")
//                //3、hasRole方法  ROLE_sale        角色
//                .antMatchers("test/index").hasRole("sale")
//                //hasAnyRole                        多个角色
//                .antMatchers("/test/index").hasAnyRole("sale")
//                .anyRequest().authenticated()           //全部需要认证
//                //自动登录
//                .and().rememberMe().tokenRepository(persistentTokenRepository())
//                .tokenValiditySeconds(60)           //设置单位
//                .userDetailsService(userDetailsService)
//                .and().csrf().disable();                 //csrf防护关闭
//    }
//
//}
