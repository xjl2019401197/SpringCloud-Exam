package com.xu.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.client.utils.StringUtils;
import com.xu.jwt.JWTUtils;
import com.xu.pojo.*;
import com.xu.redis.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Controller
public class AdminController {

    @Resource
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-user-service}")
    private String serverURL;

    @GetMapping("/testPort")
    @ResponseBody
    public String testPort() {
        return restTemplate.getForObject(serverURL + "/testPort", String.class);
    }

    @GetMapping("/logout.do")
    public String logout(HttpServletRequest request, String type) {

        Jedis jedis = JedisUtil.getJedis();
        System.out.println(type);
        jedis.expire(type,0);
        return "login";
    }

    @GetMapping("admin/home")
    public String home() {
        return restTemplate.getForObject(serverURL + "/admin/home", String.class);
    }

    @RequestMapping("/select")
    public String select() {
        return "admin/select";
    }

    @RequestMapping("/addselect")
    public String addselect() {
        return "admin/addselect";
    }

    @RequestMapping("/insertselect")
    @ResponseBody
    public String insertselect(Choice choice) {
        return restTemplate.postForObject(serverURL + "/insertselect", choice, String.class);
    }

    @GetMapping("/allselect")
    @ResponseBody
    public String allselect() {
        return restTemplate.getForObject(serverURL + "/allselect", String.class);
    }

    @PostMapping("/Uploadchoice")
    public String Uploadchoice(MultipartHttpServletRequest multipartRequest) {
        MultipartFile file = multipartRequest.getFile("file");
        System.out.println("进来了" + file);
        List<List<Object>> listob = null;
        try {

            listob = new ExcelUtils().getBankListByExcel(file.getInputStream(), file.getOriginalFilename());
            System.out.println(listob);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return restTemplate.postForObject(serverURL + "/Uploadchoice", listob, String.class);
    }

    @GetMapping("/deleteselect")
    @ResponseBody
    public R deleteselect(HttpServletRequest request) {
        String[] ids = request.getParameterValues("ids");
        ArrayList<String> list = new ArrayList<>();
        int i = 0;
        for (String id : ids) {
            list.add(id);
        }
        return restTemplate.postForObject(serverURL + "/deleteselect", list, R.class);
    }

    @RequestMapping("/updateselectjsp")
    public String updateselectjsp(HttpServletRequest request, Model model) {
        System.out.println("id=>" + request.getParameter("id"));
        Choice item = restTemplate.getForObject(serverURL + "/updateselectjsp/" + request.getParameter("id"), Choice.class);
        model.addAttribute("item", item);
        return "admin/updateselectjsp";
    }

    @RequestMapping("/updateselect")
    @ResponseBody
    public String updateselect(Choice item) {
        return restTemplate.postForObject(serverURL + "/updateselect", item, String.class);
    }


    /*
     * 判断题
     * */
    @RequestMapping("/judge")
    public String judge() {
        return "admin/judge";
    }

    @RequestMapping("/addjudge")
    public String addjudge() {
        return restTemplate.getForObject(serverURL + "/addjudge", String.class);
    }

    @RequestMapping("/insertjudge")
    @ResponseBody
    public String insertjudge(Judge judge) {
        return restTemplate.postForObject(serverURL + "/insertjudge", judge, String.class);
    }

    @GetMapping("/alljudge")
    @ResponseBody
    public String alljudge() {
        return restTemplate.getForObject(serverURL + "/alljudge", String.class);
    }

    @PostMapping("/Uploadjudge")
    public String Uploadjudge(MultipartHttpServletRequest multipartRequest) {
        MultipartFile file = multipartRequest.getFile("file");
        List<List<Object>> listob = null;
        try {

            listob = new ExcelUtils().getBankListByExcel(file.getInputStream(), file.getOriginalFilename());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restTemplate.postForObject(serverURL + "/Uploadjudge", listob, String.class);
    }

    @GetMapping("/deletejudge")
    @ResponseBody
    public R deletejudge(HttpServletRequest request) {
        return restTemplate.getForObject(serverURL + "/deletejudge/" + request.getParameter("ids"), R.class);

    }

    @RequestMapping("/updatejudgejsp")
    public String updatjudgejsp(String id, Model model) {
        System.out.println("id=" + id);
        model.addAttribute("judge", restTemplate.getForObject(serverURL + "/updatejudgejsp/" + id, Judge.class));
        return "admin/updatejudgejsp";
    }

    @RequestMapping("/updatejudge")
    @ResponseBody
    public String updatejudge(Judge judge) {
        return restTemplate.postForObject(serverURL + "/updatejudge", judge, String.class);

    }


    /*
     * Teacher
     * */
    @RequestMapping("/teacher")
    public String teacher() {
        return restTemplate.getForObject(serverURL + "/teacher", String.class);
    }

    @RequestMapping("/addteacher")
    public String addteacher() {
        return restTemplate.getForObject(serverURL + "/addteacher", String.class);

    }

    @RequestMapping("/insertteacher")
    @ResponseBody
    public String insertteacher(Teacher teacher) {
        return restTemplate.postForObject(serverURL + "/insertteacher", teacher, String.class);

    }

    @GetMapping("/allteacher")
    @ResponseBody
    public String allteacher() {
        return restTemplate.getForObject(serverURL + "/allteacher", String.class);
    }

    @GetMapping("/deleteteacher")
    @ResponseBody
    public String deleteteacher(String[] ids) {
        for (String id : ids) {
            System.out.println(id);
        }
        return restTemplate.postForObject(serverURL + "/deleteteacher", ids, String.class);

    }

    @RequestMapping("/updateteacherjsp")
    public String updateteacherjsp(HttpServletRequest request, Model model) {
        Teacher teacher = restTemplate.getForObject(serverURL + "/updateteacherjsp/" + request.getParameter("id"), Teacher.class);
        model.addAttribute("teacher", teacher);
        return "admin/updateteacherjsp";


    }

    @RequestMapping("/updateteacher")
    @ResponseBody
    public String updatetecher(Teacher teacher) {
        return restTemplate.postForObject(serverURL + "/updateteacher", teacher, String.class);
    }


    /*
     * 新建考试
     * */
    @RequestMapping("/newexam")
    public String newexam(Model model) {
        model.addAttribute("list", restTemplate.getForObject(serverURL + "/newexam", List.class));
        return "admin/newexam";
    }

    @RequestMapping("/insertexam")
    @ResponseBody
    public String insertexam(Exam exam) throws GeneralSecurityException, MessagingException {
        System.out.println(exam);
        return restTemplate.postForObject(serverURL + "/insertexam", exam, String.class);
    }

    /*
     * 查看考试
     * */

    @RequestMapping("/examinfo")
    public String examinfo(Model model) {

        List<Exam> list = restTemplate.getForObject(serverURL + "/examinfo", List.class, model);
        model.addAttribute("list", list);
        return "admin/examinfo";
    }

    @RequestMapping("/deleteexam")
    public String deleteexam(Model model, int id) {
        System.out.println("id=" + id);
        List<Exam> list = restTemplate.getForObject(serverURL + "/deleteexam/" + id, List.class);
        return "redirect:examinfo";
    }

    @RequestMapping("/getexambyid")
    @ResponseBody
    public Exam getexambyid(Integer id) {
        return restTemplate.getForObject(serverURL + "/getexambyid/" + id, Exam.class);
    }

    @RequestMapping("/updateexam")
    public String updateexam(Exam exam) {
        System.out.println(exam);
        return restTemplate.postForObject(serverURL + "/updateexam", exam, String.class);

    }

    @RequestMapping("/testStatistics")
    public String testStatistics(Model model) {
        HashMap<String, List<Object>> hashMap = restTemplate.getForObject(serverURL + "/testStatistics", HashMap.class);
        model.addAttribute("examname", hashMap.get("examname"));
        model.addAttribute("choice", hashMap.get("choice"));
        model.addAttribute("judge", hashMap.get("judge"));
        model.addAttribute("count", hashMap.get("count"));
        return "admin/testStatistics";
    }

    @RequestMapping("/numStatistics")
    public String numStatistics(Model model) {
        HashMap<String, Integer> hashMap = restTemplate.getForObject(serverURL + "/numStatistics", HashMap.class);
        model.addAttribute("student", hashMap.get("student"));
        model.addAttribute("teacher", hashMap.get("teacher"));
        model.addAttribute("manager", hashMap.get("manager"));
        return "admin/numStatistics";
    }

}
