package com.xu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xu.exception.ServiceException;
import com.xu.exception.UserPasswordNotMatchException;
import com.xu.pojo.Manager;
import com.xu.pojo.R;
import com.xu.pojo.Student;
import com.xu.pojo.Teacher;
import com.xu.service.ManagerService;
import com.xu.service.StudentService;
import com.xu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Controller

public class LoginController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ManagerService managerService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/login")
    @ResponseBody
    public String main() {
        return "拒绝访问";
    }
    @GetMapping("/port")
    @ResponseBody
    public R port() {
        return new R(false,"123");
    }

    @GetMapping("/tologin/{username}/{password}/{type}")
    @ResponseBody
    public R tologin( @PathVariable("username") String username, @PathVariable("password") String password, @PathVariable("type") String type) {
        // 用户验证
        Authentication authentication = null;
        R r = new R();
        try
        {
            redisTemplate.opsForValue().set("type",type);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(
            authenticationToken);
            Object user = authentication.getPrincipal();
            System.out.println(user);

            if(type.equals("student")){
                r.setFlag(true);
                r.setData("student/home");
                return r;
            }else if(type.equals("teacher")){
                    r.setFlag(true);
                    r.setData("teacher/home");
                    return r;
            }else{
                r.setFlag(true);
                r.setData("admin/home");
                return r;
            }
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                System.out.println(e.getMessage());
                r.setFlag(false);
                r.setData("login");
                return r;
//                throw new UserPasswordNotMatchException();
            }
            else
            {
                System.out.println(e.getMessage());
                r.setFlag(false);
                r.setData("login");
                return r;
//                throw new ServiceException(e.getMessage());
            }

        }
    }

    @RequestMapping("/register/{username}/{Password}/{type}")
    @ResponseBody
    public R register(@PathVariable String username, @PathVariable String Password, @PathVariable String type) {
        System.out.println(username + " " + Password + " " + type);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        R r = new R();
        try {
            if (type.equals("student")) {

                Student student = new Student("2019101197", username, Password, "软件学院", formatter.format(new Date(System.currentTimeMillis())).toString());
                QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("Name",student.getName());
                if(studentService.list(queryWrapper) != null)throw new Exception("重复");
                System.out.println(student);
                studentService.save(student);
            } else if (type.equals("teacher")) {
                Teacher teacher = new Teacher(0, username, Password, "助教", "2550908862@qq.com");
                QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("Name",teacher.getName());
                if(teacherService.list(queryWrapper) != null)throw new Exception("重复");
                System.out.println(teacher);
                teacherService.save(teacher);
            } else {
                Manager manager = new Manager( 0,username, Password);
                QueryWrapper<Manager> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("MName",manager.getMName());
                System.out.println(managerService.list(queryWrapper) );
                if(managerService.list(queryWrapper) != null)throw new Exception("重复");
                System.out.println(manager);
                managerService.save(manager);
            }
            r.setFlag(true);
        } catch (Exception e) {
            System.out.println(e);
            r.setFlag(false);
        } finally {
            r.setData("login");
            return r;
        }
    }
}
