<%@ page import="com.xu.jwt.JWTUtils" %>
<%@ page import="io.jsonwebtoken.Claims" %>
<%@ page import="com.xu.redis.JedisUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%><!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>学生首页</title>

    <link href="/static/tool/bootstrap/bootstrap.css" rel="stylesheet">
    <link href="/static/tool/metisMenu/metisMenu.css" rel="stylesheet">
    <link href="/static/tool/dist/sb-admin-2.css" rel="stylesheet">
    <link href="/static/tool/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <script src="/static/tool/js/123.js"></script>
    <script src="/static/tool/js/456.js"></script>
    <style>
        html {
            overflow-y: hidden;
        }
    </style>
</head>

<body>

<div id="wrapper">

    <!-- Navigation -->
    <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">中国历史考试系统</a>
        </div>
        <ul class="nav navbar-top-links navbar-right">
            <!-- /.dropdown -->
            <%
                String encode = JedisUtil.getJedis().get("student");
                Claims claims = JWTUtils.checkJWT(encode);
                String  token = (String) claims.get("username");
            %>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <i class="fa fa-user fa-fw"></i> <%=token%>&nbsp;<i
                        class="fa fa-caret-down"></i>
                </a>
                <ul class="dropdown-menu dropdown-user">
                    <li><a href="<%=basePath%>/teacher/uppass.do?username="+<%=token%>>
                        <i class="fa fa-gear fa-fw"></i> 更改密码</a>
                    </li>
                    <li class="divider"></li>
                    <li><a href="<%=basePath%>/logout.do?type=student">
                        <i class="fa fa-sign-out fa-fw"></i> 退出</a>
                    </li>
                </ul>
            </li>
        </ul>

        <div class="navbar-default sidebar" role="navigation">
            <div class="sidebar-nav navbar-collapse">
                <ul class="nav" id="side-menu">
                    <li>
                        <a href="#"><i class="fa fa-dashboard fa-fw"></i>首页</a>
                    </li>
                    <li>
                        <a href="<%=basePath%>/examlist?token=${Stuid}" target="frame">
                            <i class="fa fa-gear fa-fw"></i>考试</a>
                    </li>
                    <li>
                        <a href="<%=basePath%>/histoty?Stuid=${Stuid}" target="frame">
                            <i class="fa fa-edit fa-fw"></i>试卷回顾</a>
                    </li>
                </ul>
            </div>
            <!-- /.sidebar-collapse -->
        </div>
    </nav>

    <div id="page-wrapper" style="width: 83.5%;height: 700px;background-color: white">
        <iframe name="frame" style="width: 100%;height: 700px;background-color: white;border: black"src="<%=basePath%>/examStatistics" ></iframe>
    </div>

</div>

<script src="/static/tool/jquery/jquery.js"></script>

<script src="/static/tool/bootstrap/bootstrap.js"></script>

<script src="/static/tool/metisMenu/metisMenu.js"></script>

<script src="/static/tool/dist/sb-admin-2.js"></script>

</body>

</html>
