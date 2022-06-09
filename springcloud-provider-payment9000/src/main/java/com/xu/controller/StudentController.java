package com.xu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xu.service.*;
import com.xu.pojo.*;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private ExamService examService;

    @Autowired
    private ChoiceService choiceService;

    @Autowired
    private JudgeService judgeService;

    @Autowired
    private TestService testService;


    @RequestMapping("/student/home")
    @ResponseBody
    public String Student(){
        return "student/home";
    }
    @RequestMapping("/examlist")
    @ResponseBody
    public List<Exam> examlist(){
        return examService.list();

    }
    @RequestMapping("/judgeexist/{id}/{Stuid}")
    @ResponseBody
    public R judgeexist(@PathVariable int id, @PathVariable String Stuid){
        if(testService.getByIdAndStuid(id,Stuid) != null)
            return new R(false,"1");
        else return new R(true,"1");
    }
    @RequestMapping("/starttest/{id}")
    @ResponseBody
    public HashMap<String, Object> starttest(@PathVariable String id){
        HashMap<String, Object> hashMap = new HashMap<>();
        Exam exam = examService.getById(Integer.parseInt(id));
        String Sellist = exam.getSellist();
        String Judlist = exam.getJudlist();


        List<Integer> Selids = JSONObject.parseArray(Sellist, Integer.class);
        List<Integer> Judids = JSONObject.parseArray(Judlist, Integer.class);

        System.out.println(Selids.toString());

        ArrayList<Choice> Choicelist = new ArrayList<>();
        ArrayList<Judge> Judgelist = new ArrayList<>();

        System.out.println(exam);

        for (Integer Selid : Selids) {
            Choicelist.add(choiceService.getById(Selid));
        }
        System.out.println(Choicelist.toString());
        for (Integer Judid : Judids) {
            Judgelist.add( judgeService.getById(Judid));
        }

        System.out.println(Judgelist.toString());
        Student student = new Student("2019401197", "徐佳力", "123", "软件学院", "2019-09-12");
        hashMap.put("exam",exam);
        hashMap.put("choicelist",Choicelist);
        hashMap.put("judgelist",Judgelist);
        hashMap.put("student",student);
        return  hashMap;

    }
    @RequestMapping("/submittest")
    @ResponseBody
//    public String submittest(@PathVariable int examid, @PathVariable String studentid, @PathVariable ArrayList<String> answerchoice, @PathVariable ArrayList<String> answerjudge){
    public String submittest(@RequestBody ExamInfo examInfo){
        System.out.println(examInfo);
        int examid = examInfo.getExamid();
        String studentid = examInfo.getStudentid();
        ArrayList<String> answerchoice = examInfo.getAnswerchoice();
        ArrayList<String> answerjudge = examInfo.getAnswerjudge();

        Exam exam = examService.getById(examid);
        System.out.println(exam.toString());
        //json转换为list
        List<Integer> Sellist = JSONObject.parseArray(exam.getSellist(), Integer.class);
        List<Integer> Judlist = JSONObject.parseArray(exam.getJudlist(), Integer.class);

        System.out.println("选择题和填空题的id"+Sellist.toString() +"  "+ Judlist.toString());
        int score = 0;
        for( int i = 0; i < answerchoice.size(); i++){
            if(answerchoice.get(i).equals(choiceService.getById(Sellist.get(i)).getAnswer())  )
                score += examService.getById(examid).getSelscore();
        }
        System.out.println("answer = "+answerjudge.size() +" "+ answerjudge.size());
        for( int i = 0; i < answerjudge.size(); i++){
            if(answerjudge.get(i).equals(judgeService.getById(Judlist.get(i)).getAnswer()) )
                score += examService.getById(examid).getJudscore();
        }
        String Selanlist = JSON.toJSON(answerchoice).toString();
        String Judanlist = JSON.toJSON(answerjudge).toString();

        System.out.println("填写的选择题答案:"+Selanlist);
        System.out.println("填写的填空题答案:"+Judanlist);
        Test test = new Test(studentid, examid, exam.getSellist(), exam.getJudlist(), Selanlist, Judanlist, score);
        System.out.println("试卷:"+test.toString());
        testService.save(test);
        return "redirect:histoty";
    }

    @RequestMapping("/histoty/{Stuid}")
    @ResponseBody
    public HashMap<String, Object> histoty(@PathVariable String Stuid){
        HashMap<String, Object> hashMap = new HashMap<>();
        ArrayList<Exam> exams = new ArrayList<>();
        List<Test> tests = testService.getByStuid(Stuid);
        for (Test test : tests) {
            Exam exam = examService.getById(test.getId());
            exams.add(exam);
        }
        hashMap.put("exams",exams);
        hashMap.put("tests",tests);
        return hashMap;
    }

    @RequestMapping("/testhistory/{id}/{Stuid}")
    @ResponseBody
    public HashMap<String,Object> testhistory( @PathVariable Integer id,@PathVariable String Stuid){
        HashMap<String, Object> hashMap = new HashMap<>();
        Test test = testService.getByIdAndStuid(id, Stuid);
        String selanlist = test.getSelanlist();
        String judanlist = test.getJudanlist();

        List<String> selan = JSONObject.parseArray(selanlist, String.class);
        List<String> judan = JSONObject.parseArray(judanlist, String.class);
        System.out.println(selan.toString());
        System.out.println(judan.toString());

        Exam exam = examService.getById(id);
        String Sellist = exam.getSellist();
        String Judlist = exam.getJudlist();


        List<Integer> Selids = JSONObject.parseArray(Sellist, Integer.class);
        List<Integer> Judids = JSONObject.parseArray(Judlist, Integer.class);

        System.out.println(Selids.toString());

        ArrayList<Choice> Choicelist = new ArrayList<>();
        ArrayList<Judge> Judgelist = new ArrayList<>();

        for (Integer Selid : Selids) {
            Choicelist.add(choiceService.getById(Selid));
        }
        System.out.println(Choicelist.toString());
        for (Integer Judid : Judids) {
            Judgelist.add( judgeService.getById(Judid));
        }
        System.out.println(Judgelist.toString());
        Student student = new Student("2019401197", "徐佳力", "123", "软件学院", "2019-09-12");
        hashMap.put("exam",exam);
        hashMap.put("choicelist",Choicelist);
        hashMap.put("judgelist",Judgelist);
        hashMap.put("student",student);
        hashMap.put("selan",selan);
        hashMap.put("judan",judan);
        return hashMap;
    }
}
