package com.xu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import com.xu.service.*;
import com.xu.pojo.*;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    private StudentService studentService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ManagerService managerService;

    @Autowired
    private ChoiceService choiceService;

    @Autowired
    private JudgeService judgeService;


    @Autowired
    private ExamService examService;

    @Autowired
    private TestService testService;

    @Value("${server.port}")//获取application.yml文件中名为port的value值,并且自动完成数据类型转换
    private String port;

    @GetMapping("/testPort")
    @ResponseBody
    public String testPort() {
        return port;
    }

    @GetMapping("admin/home")
    @ResponseBody
    public String home() {
        return "admin/home";
    }

    @RequestMapping("/select")
    @ResponseBody
    public String select() {
        System.out.println("1");
        return "admin/select";
    }

    @RequestMapping("/addselect")
    @ResponseBody
    public String addselect() {
        return "admin/addselect";
    }

    @PostMapping("/insertselect")
    @ResponseBody
    public String insertselect(@RequestBody Choice choice) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (choiceService.save(choice))
                jsonObject.put("code", "0");
        } catch (Exception e) {
            jsonObject.put("code", "1");
            e.printStackTrace();
        }
        return jsonObject.toJSONString();
    }

    @GetMapping("/allselect")
    @ResponseBody
    public String allselect() {
        List<Choice> list = choiceService.getAllChoice();
        System.out.println(list);
        int count = list.size();
        String data = JSON.toJSONString(list);
        data = "{\"code\": 0,\"msg\": \"\",\"count\": " + count + ",\"data\":" + data + "}";
        return data;
    }

    @PostMapping("/Uploadchoice")
    @ResponseBody
    public String Uploadchoice(@RequestBody List<List<Object>> listob) {
        System.out.println(listob);
        for (List<Object> objects : listob) {
            Choice choice = new Choice();
            choice.setTopic((String) objects.get(0));
            choice.setA((String) objects.get(1));
            choice.setB((String) objects.get(2));
            choice.setC((String) objects.get(3));
            choice.setD((String) objects.get(4));
            choice.setAnswer((String) objects.get(5));
            choice.setType((String) objects.get(6));
            choice.setDifficulty((String) objects.get(7));
            System.out.println(choice);
            choiceService.reset();
            choiceService.save(choice);
        }
        return "redirect:select";
    }

    @GetMapping("/deleteselect/{ids}")
    @ResponseBody
    public R deleteselect(@PathVariable String[] ids) {
        R r = new R();
        r.setFlag(true);
        ArrayList<String> list = new ArrayList<>();
        List<Exam> exams = examService.list();
        String arrsellist = null;
        for (Exam exam : exams) {
            String sellist = exam.getSellist();
            arrsellist = arrsellist + sellist;
        }
        for (String id : ids) {
            try {
                if (arrsellist != null && arrsellist.contains(id)) {
                    r.setFlag(false);
                    list.add(id);
                } else {
                    choiceService.removeById(id);
                    choiceService.reset();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        r.setData(list);
        return r;
    }

    @GetMapping("/updateselectjsp/{id}")
    @ResponseBody
    public Choice updateselectjsp(@PathVariable String id) {
        System.out.println(id);
        Choice item = choiceService.getById(Integer.parseInt(id));
        System.out.println(item.toString());
        return item;
    }

    @PostMapping("/updateselect")
    @ResponseBody
    public String updateselect(@RequestBody Choice item) {
        JSONObject jsonObject = new JSONObject();
        try {
            choiceService.updateById(item);
            jsonObject.put("code", 0);
        } catch (Exception e) {
            jsonObject.put("code", 1);
        }
        return jsonObject.toJSONString();
    }


    /*
     * 判断题
     * */
    @RequestMapping("/judge")
    @ResponseBody
    public String judge() {
        return "admin/judge";
    }

    @RequestMapping("/addjudge")
    @ResponseBody
    public String addjudge() {
        return "admin/addjudge";
    }

    @RequestMapping("/insertjudge")
    @ResponseBody
    public String insertjudge(@RequestBody Judge judge) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (judgeService.save(judge))
                jsonObject.put("code", "0");
        } catch (Exception e) {
            jsonObject.put("code", "1");
            e.printStackTrace();
        }

        return jsonObject.toJSONString();
    }

    @GetMapping("/alljudge")
    @ResponseBody
    public String alljudge() {
        List<Judge> list = judgeService.getAllJudge();
        int count = list.size();
        String data = JSON.toJSONString(list);
        data = "{\"code\": 0,\"msg\": \"\",\"count\": " + count + ",\"data\":" + data + "}";
        return data;
    }

    @PostMapping("/Uploadjudge")
    @ResponseBody
    public String Uploadjudge(@RequestBody List<List<Object>> listob) {

        for (List<Object> objects : listob) {
            Judge judge = new Judge();
            judge.setContent((String) objects.get(0));
            judge.setAnswer((String) objects.get(1));
            judge.setType((String) objects.get(2));
            judge.setDifficulty((String) objects.get(3));
            if (judgeService.getByContent(judge.getContent()) != null) {
                continue;
            }

            judgeService.reset();
            judgeService.save(judge);
        }
        return "redirect:judge";
    }

    @GetMapping("/deletejudge/{ids}")
    @ResponseBody
    public R deletejudge(@PathVariable String[] ids) {
        R r = new R();
        r.setFlag(true);
        ArrayList<String> list = new ArrayList<>();
        List<Exam> exams = examService.list();
        String arrjudlist = null;
        for (Exam exam : exams) {
            String judlist = exam.getJudlist();
            arrjudlist = arrjudlist + judlist;
        }
        for (String id : ids) {
            try {
                if (arrjudlist != null && arrjudlist.contains(id)) {
                    r.setFlag(false);
                    list.add(id);
                } else {
                    judgeService.removeById(id);
                    judgeService.reset();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        r.setData(list);
        return r;
    }

    @RequestMapping("/updatejudgejsp/{id}")
    @ResponseBody
    public Judge updatjudgejsp(@PathVariable String id) {
        System.out.println("id2=" + id);
        Judge judge = judgeService.getById(Integer.parseInt(id));
        return judge;
    }

    @PostMapping("/updatejudge")
    @ResponseBody
    public String updatejudge(@RequestBody Judge judge) {
        JSONObject jsonObject = new JSONObject();
        System.out.println(judge.toString());
        try {
            judgeService.updateById(judge);
            jsonObject.put("code", 0);
        } catch (Exception e) {
            jsonObject.put("code", 1);
        }
        return jsonObject.toJSONString();
    }


    /*
     * Teacher
     * */
    @RequestMapping("/teacher")
    @ResponseBody
    public String teacher() {
        return "admin/teacher";
    }

    @RequestMapping("/addteacher")
    @ResponseBody
    public String addteacher() {
        return "admin/addteacher";
    }

    @PostMapping("/insertteacher")
    @ResponseBody
    public String insertteacher(@RequestBody Teacher teacher) {
        System.out.println(teacher.toString());
        JSONObject jsonObject = new JSONObject();
        try {
            if (teacherService.save(teacher))
                jsonObject.put("code", "0");
        } catch (Exception e) {
            jsonObject.put("code", "1");
            e.printStackTrace();
        }

        return jsonObject.toJSONString();
    }

    @GetMapping("/allteacher")
    @ResponseBody
    public String allteacher() {
        List<Teacher> list = teacherService.list();
        int count = list.size();
        String data = JSON.toJSONString(list);
        System.out.println(data);
        data = "{\"code\": 0,\"msg\": \"\",\"count\": " + count + ",\"data\":" + data + "}";
        return data;
    }

    @PostMapping("/deleteteacher")
    @ResponseBody
    public String deleteteacher(@RequestBody String[] ids) {
        for (String id : ids) {
            System.out.println(id);
        }
        String result = "ok";
        try {
            for (String id : ids) {
                teacherService.removeById(id);
            }
            teacherService.reset();
        } catch (Exception e) {
            result = "no";
            e.printStackTrace();

        }
        System.out.println(result);
        return result;
    }

    @RequestMapping("/updateteacherjsp/{id}")
    @ResponseBody
    public Teacher updateteacherjsp(@PathVariable String id) {
        Teacher teacher = teacherService.getById(Integer.parseInt(id));
        System.out.println(teacher.toString());
        return teacher;
    }

    @RequestMapping("/updateteacher")
    @ResponseBody
    public String updatetecher(@RequestBody Teacher teacher) {
        JSONObject jsonObject = new JSONObject();
        try {
            teacherService.updateById(teacher);
            jsonObject.put("code", 0);
        } catch (Exception e) {
            jsonObject.put("code", 1);
        }
        return jsonObject.toJSONString();
    }


    /*
     * 新建考试
     * */
    @RequestMapping("/newexam")
    @ResponseBody
    public List<Teacher> newexam() {
        return teacherService.list();
    }

    @PostMapping("/insertexam")
    @ResponseBody
    public String insertexam(@RequestBody Exam exam) throws GeneralSecurityException, MessagingException {
        int selnum = exam.getSelnum();
        List<Integer> Sellist = choiceService.getIdList(selnum);

        int judnum = exam.getJudnum();
        List<Integer> Judlist = judgeService.getIdList(judnum);
        exam.setSellist(JSON.toJSON(Sellist).toString());
        exam.setJudlist(JSON.toJSON(Judlist).toString());
        examService.save(exam);
        new Thread(() -> {
            try {
                SendEamil.abc();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "Thead name").start();
        JSONObject jsonObject = new JSONObject();
        try {
//            if(examService.save(exam))
            jsonObject.put("code", "0");
        } catch (Exception e) {
            jsonObject.put("code", "1");
            e.printStackTrace();
        }
        return jsonObject.toJSONString();

    }

    /*
     * 查看考试
     * */

    @RequestMapping("/examinfo")
    @ResponseBody
    public List<Exam> examinfo() {
        List<Exam> list = examService.list();
        return list;
    }

    @RequestMapping("/deleteexam/{id}")
    @ResponseBody
    public List<Exam> deleteexam(@PathVariable int id) {
        System.out.println("id=" + id);
        examService.removeById(id);
        List<Exam> list = examService.list();
        System.out.println(list);
        return list;
    }

    @RequestMapping("/getexambyid/{id}")
    @ResponseBody
    public Exam getexambyid(@PathVariable Integer id) {
        Exam exam = examService.getById(id);
        return exam;
    }

    @PostMapping("/updateexam")
    @ResponseBody
    public String updateexam(@RequestBody Exam exam) {
        System.out.println(exam);
        examService.updateById(exam);
        return "redirect:examinfo";
    }

    @GetMapping("/testStatistics")
    @ResponseBody
    public HashMap<String, List<Object>> testStatistics() {
        HashMap<String, List<Object>> hashMap = new HashMap<>();
        List<Exam> list = examService.list();
        ArrayList<Object> list1 = new ArrayList<>();
        ArrayList<Object> list2 = new ArrayList<>();
        ArrayList<Object> list3 = new ArrayList<>();
        ArrayList<Object> list4 = new ArrayList<>();
        hashMap.put("examname", list1);
        hashMap.put("choice", list2);
        hashMap.put("judge", list3);
        hashMap.put("count",list4);
        for (Exam exam : list) {
            hashMap.get("examname").add(exam.getExamname());
            hashMap.get("choice").add(exam.getSelnum());
            hashMap.get("judge").add(exam.getJudnum());
            hashMap.get("count").add(testService.getbyExamIdCount(exam.getId()));
        }

        return hashMap;
    }
    @GetMapping("/numStatistics")
    @ResponseBody
    public HashMap<String,Integer> numStatistics() {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("student",studentService.getCount());
        hashMap.put("teacher",teacherService.getCount());
        hashMap.put("manager",managerService.getCount());
        return hashMap;
    }

}
