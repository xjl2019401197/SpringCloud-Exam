package com.xu.controller;

import com.xu.jwt.JWTUtils;
import com.xu.pojo.R;
import com.xu.redis.JedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@Api(tags = "用户登录注册")
public class LoginController {
    @Resource
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-user-service}")
    private String serverURL;
    @ApiOperation("登录页面")
    @GetMapping("/login")
    public String main(){
        return "login";
    }
    @ApiOperation("登录")
    @GetMapping(value = "/tologin")
    public String tologin(HttpServletResponse response, String username, String Password, String type, Model model){
        System.out.println(username+"  "+Password +" "+type);
        R r = restTemplate.getForObject(serverURL + "/tologin/{username}/{password}/{type}", R.class, username, Password, type);
        System.out.println(r);
        if(!r.getFlag()) {
            model.addAttribute("message","用户名或密码错误");
        }
        if(r.getFlag()){
            Cookie cookie = new Cookie("username", username);
//            cookie.setPath("/");
            response.addCookie(cookie);
            String token = JWTUtils.geneJsonWebToken(username, Password, type);
            Jedis jedis = JedisUtil.getJedis();
            jedis.set(type,token);
        }
        return "redirect:"+r.getData();
    }
    @ApiOperation("注册")
    @PostMapping("/register")
    public String register(String username, String Password, String type, Model model){
        System.out.println(username+" "+Password +" "+type);
        R r = restTemplate.getForObject(serverURL + "/register/{username}/{Password}/{type}", R.class, username, Password, type);
        if(r.getFlag()){
            model.addAttribute("message","注册成功");
        }
        else {
            model.addAttribute("message","用户已存在");
        }
        return "login";
    }
}
