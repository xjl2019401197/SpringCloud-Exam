package com.xu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.xu.pojo.*;
import com.xu.service.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;

@Controller
public class TeacherController {
    @Autowired
    private ManagerService managerService;

    @Autowired
    private ChoiceService choiceService;

    @Autowired
    private JudgeService judgeService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ExamService examService;

    @Autowired
    private StudentService studentService;

    @RequestMapping("/teacher/home")
    @ResponseBody
    public String home(){
        System.out.println("didididiidi---------------------->");
        return "teacher/home";
    }
    @RequestMapping("/stuinfo")
    @ResponseBody
    public List<Student> stuinfo(){
        return studentService.list();
    }
    @RequestMapping("/deletestudent/{Stuid}")
    @ResponseBody
    public String deletestudent(@PathVariable String Stuid){
        studentService.deleteByStuid(Stuid);
        return "redirect:stuinfo";
    }
    @PostMapping("/updatestudent")
    @ResponseBody
    public String updatestudent(@RequestBody Student student){
        System.out.println(student.toString());
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Stuid",student.getStuid());
        studentService.update(student,queryWrapper);
        return "redirect:stuinfo";
    }
    @PostMapping("/addstudent")
    @ResponseBody
    public String addstudent(@RequestBody Student student){
        studentService.save(student);
        return "redirect:stuinfo";
    }
    @PostMapping("/Uploadstudent")
    @ResponseBody
    public String Uploadstudent(@RequestBody MultipartHttpServletRequest multipartRequest) throws IOException {
        MultipartFile file = multipartRequest.getFile("file");
        List<List<Object>> listob = null;
        try {
            listob = new ExcelUtils().getBankListByExcel(file.getInputStream(),file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (List<Object> objects : listob) {
            Student student = new Student();
            student.setStuid((String) objects.get(0));
            student.setName((String) objects.get(1));
            student.setPassword((String) objects.get(2));
            student.setMajor((String) objects.get(3));
            student.setAdmtime((String) objects.get(4));
            studentService.save(student);
            System.out.println(student.toString());
        }
//        System.out.println(listob.toString());
        return "redirect:stuinfo";
    }

}
