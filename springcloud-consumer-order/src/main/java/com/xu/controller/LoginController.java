package com.xu.controller;

import com.xu.jwt.JWTUtils;
import com.xu.pojo.R;
import com.xu.redis.JedisUtil;
import com.xu.redis.RedisTemplateCompoment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {
    @Resource
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-user-service}")
    private String serverURL;
    @GetMapping("/login")
    public String main(){
        return restTemplate.getForObject(serverURL + "/login",String.class);
    }
    @RequestMapping("/tologin")
    public String tologin(HttpServletRequest request,HttpServletResponse response, String id, String Password, String type, Model model){
        System.out.println(id+"  "+Password +" "+type);
        R r = restTemplate.getForObject(serverURL + "/tologin/{id}/{Password}/{type}", R.class, id, Password, type);
        if(!r.getFlag()) {
            model.addAttribute("message","用户名或密码错误");
        }
        if(r.getFlag()){
            Cookie cookie = new Cookie("id", id);
//            cookie.setPath("/");
            response.addCookie(cookie);
            String token = JWTUtils.geneJsonWebToken(id, Password, type);
            Jedis jedis = JedisUtil.getJedis();
            jedis.set(type,token);
        }
        return r.getData().toString();
    }
    @RequestMapping("/register")
    public String register(String id, String Password, String type, Model model){
        System.out.println(id+" "+Password +" "+type);
        R r = restTemplate.getForObject(serverURL + "/register/{id}/{Password}/{type}", R.class, id, Password, type);
        if(r.getFlag()){
            model.addAttribute("message","注册成功");
        }
        else {
            model.addAttribute("message","用户已存在");
        }
        return "login";
    }

}
