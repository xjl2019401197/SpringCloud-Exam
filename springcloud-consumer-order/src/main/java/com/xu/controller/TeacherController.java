package com.xu.controller;

import com.xu.pojo.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class TeacherController {

    @Resource
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-user-service}")
    private String serverURL;

    @GetMapping("/teacher/home")
    public String home(){
        return restTemplate.getForObject(serverURL + "/teacher/home",String.class);
    }
    @GetMapping("/stuinfo")
    public String stuinfo(Model model){
        ArrayList<Student> list= restTemplate.getForObject(serverURL + "/stuinfo", ArrayList.class);
        model.addAttribute("list",list);
        return "teacher/stuinfo";
    }
    @PostMapping("/deletestudent")
    public String deletestudent(String Stuid){
        return restTemplate.getForObject(serverURL + "/deletestudent/"+Stuid,String.class);

    }
    @PostMapping("/updatestudent")
    public String updatestudent( Student student){
        return restTemplate.postForObject(serverURL + "/updatestudent",student,String.class);

    }
    @PostMapping("/addstudent")
    public String addstudent( Student student){
        return restTemplate.postForObject(serverURL + "/addstudent",student,String.class);

    }
    @GetMapping("/Uploadstudent")
    public String Uploadstudent(MultipartHttpServletRequest multipartRequest) throws IOException {
        return restTemplate.postForObject(serverURL + "/Uploadstudent",multipartRequest,String.class);
    }

}
