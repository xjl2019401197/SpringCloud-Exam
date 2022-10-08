package com.xu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xu.pojo.*;
import com.xu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class StudentController {

    @Autowired
    private ExamService examService;

    @Autowired
    private ChoiceService choiceService;

    @Autowired
    private JudgeService judgeService;

    @Autowired
    private MultipleChoiceService multipleChoiceService;

    @Autowired
    private TestService testService;

    @Autowired
    private StudentService studentService;


    @RequestMapping("/student/home/{username}")
    @ResponseBody
    public R Student(@PathVariable String username){
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("Name",username);
        Student student = studentService.getOne(queryWrapper);
        R r = new R(true, student.getStuid());
        return r;

    }
    @RequestMapping("/examlist/{Stuid}")
    @ResponseBody
    public HashMap<String,Object> examlist(@PathVariable String Stuid){
        List<String> nameList = testService.getExamNameByStuid(Stuid);
        List<Exam> list = examService.list();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("nameList",nameList);
        hashMap.put("list",list);
        return hashMap;

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
        String Mullist = exam.getMultiplelist();
        List<Integer> Selids = JSONObject.parseArray(Sellist, Integer.class);
        List<Integer> Judids = JSONObject.parseArray(Judlist, Integer.class);
        List<Integer> Multipleids = JSONObject.parseArray(Mullist, Integer.class);


        ArrayList<Choice> Choicelist = new ArrayList<>();
        ArrayList<Judge> Judgelist = new ArrayList<>();
        ArrayList<MultipleChoice> Multiplelist = new ArrayList<>();

        System.out.println(exam);

        for (Integer Selid : Selids) {
            Choicelist.add(choiceService.getById(Selid));
        }
        System.out.println(Choicelist.toString());
        for (Integer Judid : Judids) {
            Judgelist.add( judgeService.getById(Judid));
        }
        for (Integer Multipleid : Multipleids) {
            Multiplelist.add( multipleChoiceService.getById(Multipleid));
        }
        Student student = new Student("2019401197", "徐佳力", "123", "软件学院", "2019-09-12");
        hashMap.put("exam",exam);
        hashMap.put("choicelist",Choicelist);
        hashMap.put("judgelist",Judgelist);
        hashMap.put("multiplelist",Multiplelist);
        hashMap.put("student",student);
        return  hashMap;

    }
    @RequestMapping("/submittest")
    @ResponseBody
//    public String submittest(@PathVariable int examid, @PathVariable String studentid, @PathVariable ArrayList<String> answerchoice, @PathVariable ArrayList<String> answerjudge){
    public String submittest(@RequestBody ExamInfo examInfo){
        System.out.println(examInfo);
        int examid = examInfo.getExamid();
        String Stuid = examInfo.getStudentid();
        ArrayList<String> answerchoice = examInfo.getAnswerchoice();
        ArrayList<String> answerjudge = examInfo.getAnswerjudge();
        ArrayList<String> answermultiple = examInfo.getAnswermultiple();

        Exam exam = examService.getById(examid);
        System.out.println(exam.toString());
        //json转换为list
        List<Integer> Sellist = JSONObject.parseArray(exam.getSellist(), Integer.class);
        List<Integer> Judlist = JSONObject.parseArray(exam.getJudlist(), Integer.class);
        List<Integer> Multiplelist = JSONObject.parseArray(exam.getMultiplelist(), Integer.class);

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
        for( int i = 0; i < answermultiple.size(); i++){
            if(answermultiple.get(i).equals(multipleChoiceService.getById(Multiplelist.get(i)).getAnswer()) )
                score += examService.getById(examid).getJudscore();
        }
        String Selanlist = JSON.toJSON(answerchoice).toString();
        String Judanlist = JSON.toJSON(answerjudge).toString();
        String Mulanlist = JSON.toJSON(answermultiple).toString();

        System.out.println("填写的选择题答案:"+Selanlist);
        System.out.println("填写的判断题答案:"+Judanlist);
        System.out.println("填写的多选题答案:"+Mulanlist);
        Test test = new Test( examid,Stuid, exam.getSellist(), exam.getJudlist(), exam.getMultiplelist(),Selanlist, Judanlist,Mulanlist, score);
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
        String mulanlist = test.getMulanlist();

        List<String> selan = JSONObject.parseArray(selanlist, String.class);
        List<String> judan = JSONObject.parseArray(judanlist, String.class);
        List<String> mulan = JSONObject.parseArray(mulanlist, String.class);

        Exam exam = examService.getById(id);
        String Sellist = exam.getSellist();
        String Judlist = exam.getJudlist();
        String Mullist = exam.getMultiplelist();


        List<Integer> Selids = JSONObject.parseArray(Sellist, Integer.class);
        List<Integer> Judids = JSONObject.parseArray(Judlist, Integer.class);
        List<Integer> Mulids = JSONObject.parseArray(Mullist, Integer.class);

        System.out.println(Selids.toString());

        ArrayList<Choice> Choicelist = new ArrayList<>();
        ArrayList<Judge> Judgelist = new ArrayList<>();
        ArrayList<MultipleChoice> Multiplelist = new ArrayList<>();

        for (Integer Selid : Selids) {
            Choicelist.add(choiceService.getById(Selid));
        }
        System.out.println(Choicelist.toString());
        for (Integer Judid : Judids) {
            Judgelist.add( judgeService.getById(Judid));
        }
        System.out.println(Judgelist.toString());
        for (Integer Mulid : Mulids) {
            Multiplelist.add( multipleChoiceService.getById(Mulid));
        }
        System.out.println(Multiplelist.toString());
        Student student = new Student("2019401197", "徐佳力", "123", "软件学院", "2019-09-12");
        hashMap.put("exam",exam);
        hashMap.put("choicelist",Choicelist);
        hashMap.put("judgelist",Judgelist);
        hashMap.put("multiplelist",Multiplelist);
        hashMap.put("student",student);
        hashMap.put("selan",selan);
        hashMap.put("judan",judan);
        hashMap.put("mulan",mulan);
        System.out.println("score:"+test.getScore());
        hashMap.put("score",test.getScore());
        return hashMap;
    }
    @GetMapping("/examStatistics")
    @ResponseBody
    public HashMap<String, List> examStatistics() {
        ArrayList<String> days = getDays(7);
        HashMap<String, List> hashMap = new HashMap<>();
        ArrayList<String> daysList = new ArrayList<>();
        ArrayList<Long> numsList = new ArrayList<>();
        for (String day : days) {
            daysList.add(day);
            QueryWrapper<Exam> wrapper = new QueryWrapper<>();
            wrapper.like("Begindate",day);
            numsList.add(examService.count(wrapper));
        }
        hashMap.put("daysList",daysList);
        hashMap.put("numsList",numsList);
        return hashMap;
    }
    /**
     * 获取过去7天内的日期数组
     * @param intervals      intervals天内
     * @return              日期数组
     */
    public ArrayList<String> getDays(int intervals) {
        ArrayList<String> pastDaysList = new ArrayList<>();
        for (int i = intervals -1; i >= 0; i--) {
            pastDaysList.add(getPastDate(i));
        }
        return pastDaysList;
    }
    /**
     * 获取过去第几天的日期
     * @param past
     * @return
     */
    public String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("y-M-d");
        String result = format.format(today);
        return result;
    }
}
