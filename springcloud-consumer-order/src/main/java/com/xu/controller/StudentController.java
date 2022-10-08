package com.xu.controller;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xu.jwt.JWTUtils;
import com.xu.pojo.*;
import com.xu.redis.JedisUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Controller
public class StudentController {


    @Resource
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-user-service}")
    private String serverURL;

    @GetMapping("/student/home")
    public String Student(Model model){
        String encode = JedisUtil.getJedis().get("student");
        Claims claims = JWTUtils.checkJWT(encode);
        String  username = (String) claims.get("username");
        R r = restTemplate.getForObject(serverURL + "/student/home/"+username, R.class);
        model.addAttribute("Stuid",r.getData());
        return "student/home";
    }
    @GetMapping("/examlist")
    public String examlist( @RequestParam String token, Model model){
        System.out.println(token);
        HashMap<String,Object> map= restTemplate.getForObject(serverURL + "/examlist/"+token,HashMap.class);
        List<String> nameList = (List<String>) map.get("nameList");
        List<Exam> list = (List<Exam>) map.get("list");
        model.addAttribute("token",token);
        model.addAttribute("nameLst",nameList);
        model.addAttribute("list",list);
        return "student/examlist";
    }
    @GetMapping("/judgeexist")
    @ResponseBody
    public R judgeexist(@RequestParam int id,@RequestParam String Stuid){
        return restTemplate.getForObject(serverURL + "/judgeexist/"+id+"/"+Stuid,R.class);

    }
    @GetMapping("/starttest")
    public String starttest(Model model,@RequestParam String id,@RequestParam String Stuid) throws IOException {
        System.out.println(id);
        HashMap<String, Object> hashMap = restTemplate.getForObject(serverURL + "/starttest/" + id, HashMap.class);
        ObjectMapper objectMapper = new ObjectMapper();
        String examStr = objectMapper.writeValueAsString(hashMap.get("exam"));
        String studentStr = objectMapper.writeValueAsString(hashMap.get("student"));
        ArrayList<Choice> Choicelist = new ArrayList<>();
        ArrayList<Judge>  Judgelist= new ArrayList<>();
        ArrayList<MultipleChoice>  Multiplelist= new ArrayList<>();


        Exam exam = objectMapper.readValue(examStr, Exam.class);
        Student student = objectMapper.readValue(studentStr, Student.class);
        Object choicelist = hashMap.get("choicelist");
        Object judgelist = hashMap.get("judgelist");
        Object multiplelist = hashMap.get("multiplelist");


        for(Object o: (List<?>) choicelist)
        {
            String choice = objectMapper.writeValueAsString(o);

            Choicelist.add(objectMapper.readValue(choice, Choice.class)) ;
        }

        for(Object o: (List<?>) judgelist)
        {
            String judge = objectMapper.writeValueAsString(o);

            Judgelist.add(objectMapper.readValue(judge, Judge.class)) ;
        }

        for(Object o: (List<?>) multiplelist)
        {
            String multiple = objectMapper.writeValueAsString(o);

            Multiplelist.add(objectMapper.readValue(multiple, MultipleChoice.class)) ;
        }
        model.addAttribute("Stuid",Stuid);
        model.addAttribute("exam",exam);
        model.addAttribute("choicelist",Choicelist);
        model.addAttribute("judgelist",Judgelist);
        model.addAttribute("multiplelist",Multiplelist);
        model.addAttribute("student",student);
        return "student/exam";
    }

    @GetMapping("/submittest")
    public String submittest(int examid, String studentid, @RequestParam ArrayList<String> answerchoice, @RequestParam ArrayList<String> answerjudge,@RequestParam ArrayList<String> answermultiple){
        // 1、使用postForObject请求接口
        return restTemplate.postForObject(serverURL + "/submittest",new ExamInfo(examid,studentid,answerchoice,answerjudge,answermultiple),String.class);
    }
    @GetMapping("/histoty")
    public String histoty(Model model,String Stuid) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String,Object> hashMap = restTemplate.getForObject(serverURL + "/histoty/"+"2019401190", HashMap.class);
        System.out.println("hashMap="+hashMap+"Stuid="+Stuid);
        Object examlist = hashMap.get("exams");
        Object testlist = hashMap.get("tests");
        ArrayList<Exam> exams = new ArrayList<>();
        ArrayList<Test> tests = new ArrayList<>();
        for(Object o: (List<?>) examlist)
        {
            String exam = objectMapper.writeValueAsString(o);

            exams.add(objectMapper.readValue(exam, Exam.class)) ;
        }
        for(Object o: (List<?>) testlist)
        {
            String test = objectMapper.writeValueAsString(o);

            tests.add(objectMapper.readValue(test, Test.class)) ;
        }
        model.addAttribute("Stuid",Stuid);
        model.addAttribute("exams",exams);
//        System.out.println(exams);
//        System.out.println(tests);
        model.addAttribute("tests",tests);
        return "student/histoty";
    }

    @GetMapping("/testhistory")
    public String testhistory(Model model, @RequestParam Integer id,@RequestParam String Stuid) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String ,Object> hashMap = restTemplate.getForObject(serverURL + "/testhistory/"+id+"/"+"2019401190", HashMap.class);
        String examStr = objectMapper.writeValueAsString(hashMap.get("exam"));
        String studentStr = objectMapper.writeValueAsString(hashMap.get("student"));
        System.out.println(hashMap);

        ArrayList<Choice> Choicelist = new ArrayList<>();
        ArrayList<Judge>  Judgelist= new ArrayList<>();
        ArrayList<MultipleChoice>  Multiplelist= new ArrayList<>();
        ArrayList<String> Sellist = new ArrayList<>();
        ArrayList<String> Judlist = new ArrayList<>();
        ArrayList<String> Multlist = new ArrayList<>();

        Exam exam = objectMapper.readValue(examStr, Exam.class);
        Student student = objectMapper.readValue(studentStr, Student.class);
        Object choicelist = hashMap.get("choicelist");
        Object judgelist = hashMap.get("judgelist");
        Object multiplelist = hashMap.get("multiplelist");
        Object sellist = hashMap.get("selan");
        Object judlist = hashMap.get("judan");
        Object mullist = hashMap.get("mulan");
        Object score = hashMap.get("score");

        for(Object o: (List<?>) choicelist)
        {
            String choice = objectMapper.writeValueAsString(o);

            Choicelist.add(objectMapper.readValue(choice, Choice.class)) ;
        }

        for(Object o: (List<?>) judgelist)
        {
            String judge = objectMapper.writeValueAsString(o);

            Judgelist.add(objectMapper.readValue(judge, Judge.class)) ;
        }
        for(Object o: (List<?>) multiplelist)
        {
            String multiple = objectMapper.writeValueAsString(o);

            Multiplelist.add(objectMapper.readValue(multiple, MultipleChoice.class)) ;
        }
        for(Object o: (List<?>) sellist)
        {
            String sel = objectMapper.writeValueAsString(o);

            Sellist.add(objectMapper.readValue(sel, String.class)) ;
        }
        for(Object o: (List<?>) judlist)
        {
            String jud = objectMapper.writeValueAsString(o);

            Judlist.add(objectMapper.readValue(jud, String.class)) ;
        }
        for(Object o: (List<?>) mullist)
        {
            String mul = objectMapper.writeValueAsString(o);

            Multlist.add(objectMapper.readValue(mul, String.class)) ;
        }
        model.addAttribute("exam",exam);
        model.addAttribute("choicelist",Choicelist);
        model.addAttribute("judgelist",Judgelist);
        model.addAttribute("multiplelist",Multiplelist);
        model.addAttribute("student",student);
        model.addAttribute("selan",Sellist);
        model.addAttribute("judan",Judlist);
        model.addAttribute("Mulan",Multlist);
        model.addAttribute("score",score);
        return "student/testhistory";
    }


    @GetMapping("/examStatistics")
    public String testStatistics(Model model) {
        HashMap<String, List<Object>> hashMap = restTemplate.getForObject(serverURL + "/testStatistics", HashMap.class);
        model.addAttribute("numsList",hashMap.get("numsList"));
        model.addAttribute("daysList",hashMap.get("daysList"));
        return "student/examStatistics";
    }
}
