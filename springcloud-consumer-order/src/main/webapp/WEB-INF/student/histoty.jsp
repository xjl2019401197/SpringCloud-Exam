<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: No
  Date: 2021/12/20
  Time: 11:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="/static/tool/bootstrap/bootstrap.css" rel="stylesheet">
    <link href="/static/tool/metisMenu/metisMenu.css" rel="stylesheet">
    <link href="/static/tool/dist/sb-admin-2.css" rel="stylesheet">
    <link href="/static/tool/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <script src="/static/tool/js/123.js"></script>
    <script src="/static/tool/js/456.js"></script>
    <style>
        .table th {
            text-align: center;
        }

        .table td {
            text-align: center;
        }
    </style>
</head>
<body style="background-color: white">
<div id="wrapper">
    <!-- Page Content -->
    <br>
    <table class="table table-striped" style="width: 90% " align="center">
        <caption></caption>
        <thead>
        <tr>
            <th width="10%">考试名称</th>
            <th width="25%">考试时间</th>
            <th width="10%">时长</th>
            <th width="15%">成绩</th>
            <th width="10%">命题人</th>
            <th width="10%">操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${exams}" var="exam" varStatus="status">
            <tr>
                <td>
                    <a href="<%=request.getContextPath()%>/testhistory?id=${exam.id}&Stuid=${Stuid}">${exam.examname}</a>
                </td>
                <td>${exam.begindate}&nbsp;${exam.enddate}</td>
                <td>${exam.examtime}</td>
                <td>${tests.get(status.index).score}</td>
                <td>${exam.teacher}</td>
                <td><button type="button" onclick="window.location.href='<%=request.getContextPath()%>/testhistory?id=${exam.id}&Stuid=${Stuid}'">打印</button> </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script src="/static/tool/jquery/jquery.js"></script>

<script src="/static/tool/bootstrap/bootstrap.js"></script>

<script src="/static/tool/metisMenu/metisMenu.js"></script>

<script src="/static/tool/dist/sb-admin-2.js"></script>

<script>
    function chicking(password, id) {
        console.log("考试密码 " + password);
        console.log("试卷id " + id);
        var name = prompt("请输入密码", ""); // 弹出input框
        console.log("输入的密码" + name);
        if (name == password) {

            location.href = "<%=request.getContextPath()%>/starttest?id=" + id+"&Stuid="+${Stuid};
        } else {
            alert("密码错误");
        }
    }
</script>
</body>
</html>
