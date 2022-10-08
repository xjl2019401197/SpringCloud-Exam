package com.xu.config;


import com.xu.redis.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;
import redis.clients.jedis.Jedis;

/**
 * spring security配置
 *
 * @author ruoyi
 */
@Configuration
//开启注解securedEnabled、prePostEnabled
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    /**
     * 自定义用户认证逻辑
     */
    @Autowired
    private UserDetailsService userDetailsService;


    /**
     * 退出处理类
     */
    @Autowired
    private LogoutSuccessHandlerImpl logoutSuccessHandler;


    /**
     * 跨域过滤器
     */
    @Autowired
    private CorsFilter corsFilter;


    /**
     * 解决 无法直接注入 AuthenticationManager
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    /**
     * anyRequest          |   匹配所有请求路径
     * access              |   SpringEl表达式结果为true时可以访问
     * anonymous           |   匿名可以访问
     * denyAll             |   用户不能访问
     * fullyAuthenticated  |   用户完全认证可以访问（非remember-me下自动登录）
     * hasAnyAuthority     |   如果有参数，参数表示权限，则其中任何一个权限可以访问
     * hasAnyRole          |   如果有参数，参数表示角色，则其中任何一个角色可以访问
     * hasAuthority        |   如果有参数，参数表示权限，则其权限可以访问
     * hasIpAddress        |   如果有参数，参数表示IP地址，如果用户IP和参数匹配，则可以访问
     * hasRole             |   如果有参数，参数表示角色，则其角色可以访问
     * permitAll           |   用户可以任意访问
     * rememberMe          |   允许通过remember-me登录的用户访问
     * authenticated       |   用户登录后可访问
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception
    {
        System.out.println("页面请求");

        //自定义403页面
//        http.exceptionHandling().accessDeniedPage("/unauth");
//        http.authorizeRequests().anyRequest().permitAll();
        //自定义退出页面
        Jedis jedis = JedisUtil.getJedis();
        if (jedis.get("admin") != null || jedis.get("teacher") != null || jedis.get("student") != null) {
            System.out.println(jedis.get("admin"));
            System.out.println(jedis.get("teacher"));
            System.out.println(jedis.get("student"));
            httpSecurity.authorizeRequests().antMatchers("/**").permitAll();
        }
        httpSecurity.logout().logoutUrl("/logout").logoutSuccessUrl("/test/hello").permitAll();
        httpSecurity.formLogin()        //自定义登录页面
                .loginPage("/login")                //登录页面
                .loginProcessingUrl("/tologin")      //登录访问路径
                .permitAll()       //登录成功后，跳转的路径
                .and().authorizeRequests()
                .antMatchers("/login", "/register", "/tologin/**", "/toregister","doc.html").permitAll()      //设置不需要认证路径
                .antMatchers("/static/**").permitAll()      //设置不需要认证路径
                .antMatchers("/webjars/**").permitAll()      //设置不需要认证路径
                .antMatchers("/testPort").permitAll()      //设置不需要认证路径
                .anyRequest().authenticated()           //全部需要认证
                .and().csrf().disable();                 //csrf防护关闭
        httpSecurity.headers().frameOptions().disable();
        // 添加Logout filter
        httpSecurity.logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);

    }

    /**
     * 强散列哈希加密实现
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    /**
     * 身份认证接口
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService);
    }
}
