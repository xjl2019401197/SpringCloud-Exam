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
            <th width="15%">时长</th>
            <th width="10%">类型</th>
            <th width="10%">状态</th>
            <th width="10%">命题人</th>
        </tr>
        </thead>
        <tbody>
        <%
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String now = sdf.format(new Date());
            request.setAttribute("currentTime", now);
        %>

        <c:forEach items="${list}" var="exam">
            <tr>
                <td>
                    <c:choose>
                        <c:when test="${exam.enddate < currentTime}">
                            ${exam.examname}
                        </c:when>
                        <c:when test="${exam.begindate > currentTime}">
                            ${exam.examname}
                        </c:when>
                        <c:when test="${empty exam.password}">
                            <a href="javascript:void(0);" id="rowid1"
                               onclick="changeurl(${exam.id});">${exam.examname}</a>
                            <%--                                <a href="<%=request.getContextPath()%>/starttest?id=${exam.id}">${exam.examname}</a>--%>
                        </c:when>
                        <c:otherwise>
                            <a onclick="chicking(${exam.password},${exam.id})">${exam.examname}</a>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${exam.begindate}&nbsp;${exam.enddate}</td>
                <td>${exam.examtime}</td>
                <td>
                    <c:choose>
                        <c:when test="${empty exam.password}">
                            无
                        </c:when>
                        <c:otherwise>
                            私有
                        </c:otherwise>
                    </c:choose>
                </td>
                <c:choose>
                    <c:when test="${exam.enddate < currentTime}">
                        <td style="color:red">已结束</td>
                    </c:when>
                    <c:when test="${exam.begindate > currentTime}">
                        <td style="color:blue">未开始</td>
                    </c:when>
                    <c:otherwise>
                        <td style="color:green">进行中</td>
                    </c:otherwise>
                </c:choose>
                <td>${exam.teacher}</td>
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

    var changeurl = function (id) {
        $.ajax({
            url: '${pageContext.request.contextPath}/judgeexist?id=' + id+"&Stuid="+${token},//ajax请求
            dataType: 'text',//返回的数据类型
            success: function (data) {//回调函数，根据返回结果进行相应的操作
                var jsonObject = eval("(" + data + ")");
                if (jsonObject.flag == true) location.href = "<%=request.getContextPath()%>/starttest?id="+id+"&Stuid="+${token};
                else alert("只能进行一次考试")
            }
    });
    }

    function chicking(password, id) {
        console.log("考试密码 " + password);
        console.log("试卷id " + id);
        var name = prompt("请输入密码", ""); // 弹出input框
        console.log("输入的密码" + name);
        if (name == password) {
            changeurl(id)
        } else {
            alert("密码错误");
        }
    }
</script>
</body>
</html>
