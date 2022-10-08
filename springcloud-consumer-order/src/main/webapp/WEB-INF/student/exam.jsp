<%@ page import="java.awt.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%><!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">

    <title>Title</title>
    <meta name="referrer" content="never">
    <link href="/static/tool/bootstrap/bootstrap.css" rel="stylesheet">
    <link href="/static/tool/metisMenu/metisMenu.css" rel="stylesheet">
    <link href="/static/tool/dist/sb-admin-2.css" rel="stylesheet">
    <link href="/static/tool/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <script src="/static/tool/js/123.js"></script>
    <script src="/static/tool/js/456.js"></script>

    <!-- 引入 layui.css -->
    <link rel="stylesheet" href="/static/tool/layui-v2.6.7/layui/css/layui.css" media="all">

    <!-- 引入 layui.js -->
    <script src="/static/tool/layui-v2.6.7/layui/layui.js"></script>
    <SCRIPT type="text/javascript">
        var maxtime =
        ${exam.examtime} *
        60;

        function CountDown() {
            if (maxtime > 0) {
                minutes = Math.floor(maxtime / 60);
                seconds = Math.floor(maxtime % 60);
                msg = minutes + "分" + seconds + "秒";
                document.all["timer"].innerHTML = msg;
                if (maxtime == 5 * 60) alert("还剩5分钟");
                --maxtime;
            } else {
                clearInterval(timer);
                getValue();
                alert("时间到，结束!");
            }
        }

        timer = setInterval("CountDown()", 1000);
    </SCRIPT> <!--倒计时-->
    <style type="text/css">
        .opt {
            width: 30px;
            height: 30px;
            border: 1px solid gray;
            float: left;

        }

        .opt_later {
            width: 30px;
            height: 30px;
            border: 1px solid gray;
            float: left;
            background-color: gray;
        }
    </style>
</head>

<body>
<div id="wrapper">
    <div>
        <table border="0" width="90%" cellpadding="0" style="min-width:860px;">
            <tr>
                <!-- left -->
                <td valign="top">
                    <div class="tm_paper">
                        <div class="tm_paper_head">
                            <h5 style="background:#a6b4cd; color: #ffffff; padding:15px 0; font-size:14px; font-weight:bold; text-align: center">
                                ${exam.examname}
                            </h5>
                            <h5>
                                <b>开考时间</b> :
                                ${exam.begindate}-${exam.enddate}
                                &nbsp;&nbsp;
                                <b>考试时长</b> :${exam.examtime}分钟
                                &nbsp;&nbsp;
                                <b>选择题每小题分数</b> : ${exam.selscore}
                                &nbsp;&nbsp;
                                <b>判断题每小题分数</b> : ${exam.judscore}
                                &nbsp;&nbsp;
                                <b>出卷人</b> : ${exam.teacher}
                            </h5>
                            <h5 style="background:#a6b4cd; color: #ffffff; padding:5px 0; font-size:14px; font-weight:bold">
                                考生信息
                            </h5>
                            <h5 style="margin-top: 10px">
                                <b>考生学号</b> :
                                ${student.stuid}
                                &nbsp;&nbsp;
                                <b>考生姓名</b> :
                                ${student.name}
                                &nbsp;&nbsp;

                                <b>倒计时(分钟)</b> :
                                <em id="timer" align="right" style="color :red"></em>
                                <em id="warring" style="color:red"></em>
                                &nbsp;&nbsp;
                            </h5>
                        </div>

                    </div>
                </td>
            </tr>
        </table>
    </div>
    <%
        int selindex = 0;
        int judindex = 0;
        int mulindex = 0;
    %>
    <div  style="position: absolute; right: 130px; width: 300px;height: 300px;border: 0 solid white;">
        <iframe id="frame" name="frame" src="http://127.0.0.1:5001/" style="width: 300px;height: 300px; border: 0 solid white;"scrolling="no">
        </iframe>
<%--        <img src="http://127.0.0.1:5001/" style="width: 300px;height: 300px" />--%>
    </div>


    <div class="container">
        <div class="row">
            <div class="col-sm-8" style="top: 20px">
                <br/>
                <h3>一、单选题（每题${exam.selscore}分）</h3>
                <c:forEach items="${choicelist}" var="ch" varStatus="list">
                    <form class="layui-form">
                        <fieldset>
                            <div>
                                <h3 style="margin-top: 10px;margin-bottom: 10px"><%=++selindex%>、${ch.topic}</h3>
                            </div>
                            <input class="layui-form-radio" type="radio" name="Select" value="A"/>A: ${ch.a}<br/>
                            <input class="layui-form-radio" type="radio" name="Select" value="B"/>B: ${ch.b}<br/>
                            <input class="layui-form-radio" type="radio" name="Select" value="C"/>C: ${ch.c}<br/>
                            <input class="layui-form-radio" type="radio" name="Select" value="D"/>D: ${ch.d}<br/>
                            <br/>
                        </fieldset>
                    </form>
                </c:forEach>
                <br/>
                <br/>
                <br/>

                <h3>二、判断题（每题${exam.judscore}分）</h3>
                <c:forEach items="${judgelist}" var="ju">
                    <form class="layui-form">
                        <fieldset>
                            <div>
                                <h3 style="margin-top: 10px;margin-bottom: 10px"><%=++judindex%>、${ju.content}</h3>
                            </div>
                            <input class="layui-form-radio" type="radio" name="Judge" value="正确"/>正确<br/>
                            <input class="layui-form-radio" type="radio" name="Judge" value="错误"/>错误<br/>
                            <br/>
                        </fieldset>
                    </form>
                </c:forEach>

                <br/>
                <br/>
                <br/>

                <h3>三、多选题题（每题${exam.multiplescore}分）</h3>
                <c:forEach items="${multiplelist}" var="mul">
                    <form class="layui-form">
                        <fieldset>
                            <div>
                                <h3 style="margin-top: 10px;margin-bottom: 10px"><%=++mulindex%>、${mul.topic}</h3>
                            </div>
                                 <input type="checkbox" name="Multiple"  value="A"><span style="margin-left: 20px;margin-right: 10px">A:</span>${mul.a}<br/>
                                 <input type="checkbox" name="Multiple"  value="B"><span style="margin-left: 20px;margin-right: 10px">B:</span>${mul.b}<br/>
                                 <input type="checkbox" name="Multiple"  VALUE="C"><span style="margin-left: 20px;margin-right: 10px">C:</span>${mul.c}<br/>
                                 <input type="checkbox" name="Multiple"  value="D"><span style="margin-left: 20px;margin-right: 10px">D:</span>${mul.d}<br/>
                            <br/>
                        </fieldset>
                    </form>
                </c:forEach>

            </div>
        </div>

        <br/>
        <br/>

        <p align="center" style="margin-top: 30px">
            <button style="width: 40%" onclick="getValue()" type="button" class="btn btn-default btn-lg btn-block">提交试卷
            </button>
        </p>

        <br/>
        <br/>
        <br/>
        <br/>
    </div>
</div>
<!-- /#page-wrapper -->

<!-- /#wrapper -->

<!-- jQuery -->

<script src="/static/tool/jquery/jquery.js"></script>

<script src="/static/tool/bootstrap/bootstrap.js"></script>

<script src="/static/tool/metisMenu/metisMenu.js"></script>

<script src="/static/tool/dist/sb-admin-2.js"></script>

</body>
<script>

    var getValue = function () {
        var Select = document.getElementsByName("Select");
        var Judge = document.getElementsByName("Judge");
        var Judge = document.getElementsByName("Judge");
        var Multiple = document.getElementsByName("Multiple");
        var answerchoice = new Array();
        var answerjudge = new Array();
        var answermultiple = new Array();
        for (i = 0; i < Select.length; i++) {
            if (Select[i].checked) {
                answerchoice.push(Select[i].value);
            }
        }
        for (i = 0; i < Judge.length; i++) {
            if (Judge[i].checked) {
                answerjudge.push(Judge[i].value);
            }
        }
        for (i = 0; i < Multiple.length / 4; i++) {
            var temp= "";
            var pom = ['A','B','C','D']
            for ( j = 0; j < 4; j ++){
                if(Multiple[i*4+j].checked)
                temp += pom[j];
            }
            answermultiple.push(temp);
            // if (Multiple[i].checked) {
            //     alert(Multiple[i].checked)
            //     // answerjudge.push(Judge[i].value);
            // }
        }
        if (answerchoice.length < ${exam.selnum} || answerjudge.length < ${exam.judnum} || answermultiple < ${exam.multiplenum}) {
            alert("还有未选择的题目");
        } else {
            location.href = "<%=basePath%>/submittest?examid=" + ${exam.id}+
                '&studentid=' + ${Stuid} +'&answerchoice=' + answerchoice + '&answerjudge=' + answerjudge+'&answermultiple=' + answermultiple ;
            // alert("选择答案" +  answerchoice + "填空题答案" + answerjudge);
        }
    }
</script>
</html>
