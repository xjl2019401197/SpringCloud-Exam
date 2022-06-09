package com.xu.controller;

import com.xu.service.*;
import com.xu.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class LoginController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ManagerService managerService;

    @GetMapping("/login")
    @ResponseBody
    public String main() {
        return "login";
    }

    @RequestMapping("/tologin/{id}/{Password}/{type}")
    @ResponseBody
    public R tologin(HttpServletRequest request, @PathVariable String id, @PathVariable String Password, @PathVariable String type) {
        System.out.println(id + " " + Password + " " + type);
        R r = new R();
        if (type.equals("student")) {
            Student student = studentService.getStudentByIP(id, Password);
            if (student != null) {
                System.out.println(student);
                r.setFlag(true);
                r.setData("student/home");
                return r;
            }
        } else if (type.equals("teacher")) {
            Teacher teacher = teacherService.getTeacherByIP(id, Password);
            if (teacher != null) {
                System.out.println(teacher);
                r.setFlag(true);
                r.setData("teacher/home");
                return r;
            }
        } else {
            Manager manager = managerService.getManagerByIP(id, Password);
            if (manager != null) {
                System.out.println(manager);
                r.setFlag(true);
                r.setData("admin/home");
                return r;
            }
        }
        r.setFlag(false);
        r.setData("login");
        return r;
    }

    @RequestMapping("/register/{id}/{Password}/{type}")
    @ResponseBody
    public R register(@PathVariable String id, @PathVariable String Password, @PathVariable String type) {
        System.out.println(id + " " + Password + " " + type);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        R r = new R();
        try {
            if (type.equals("student")) {
                Student student = new Student(id, "user", Password, "软件学院", formatter.format(new Date(System.currentTimeMillis())).toString());
                studentService.save(student);
            } else if (type.equals("teacher")) {
                Teacher teacher = new Teacher(Integer.parseInt(id), "root", Password, "助教", "2550908862@qq.com");
                teacherService.save(teacher);
            } else {
                Manager manager = new Manager(Integer.parseInt(id), "admin", Password);
                managerService.save(manager);
            }
            r.setFlag(true);
        } catch (Exception e) {
            r.setFlag(false);
        } finally {
            r.setData("login");
            return r;
        }
    }
}
