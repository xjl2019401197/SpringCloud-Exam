package com.xu.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.xu.exception.ServiceException;
import com.xu.pojo.Manager;
import com.xu.pojo.Student;
import com.xu.pojo.Teacher;
import com.xu.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;


/**
 * 用户验证处理
 *
 * @author ruoyi
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ManagerService managerService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        String type = (String) redisTemplate.opsForValue().get("type");

        if (type.equals("student")) {
            QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("Name",username);
            Student student = studentService.getOne(queryWrapper);
            if(StringUtils.isNull(student)){
                log.info("登录用户：{} 不存在.", username);
                throw new ServiceException("登录用户：" + username + " 不存在");
            }
            return new User(username,new BCryptPasswordEncoder().encode(student.getPassword() ), new HashSet<>());
        } else if (type.equals("teacher")) {
            QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("Name",username);
            Teacher teacher = teacherService.getOne(queryWrapper);
            if(StringUtils.isNull(teacher)){
                log.info("登录用户：{} 不存在.", username);
                throw new ServiceException("登录用户：" + username + " 不存在");
            }

            return new User(username,new BCryptPasswordEncoder().encode(teacher.getPassword() ), new HashSet<>());
        } else {
            QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("MName",username);
            Manager manager = managerService.getOne(queryWrapper);
            if(StringUtils.isNull(manager)){
                log.info("登录用户：{} 不存在.", username);
                throw new ServiceException("登录用户：" + username + " 不存在");
            }

            return new User(username,new BCryptPasswordEncoder().encode(manager.getMPsw() ), new HashSet<>());
        }
    }
}
