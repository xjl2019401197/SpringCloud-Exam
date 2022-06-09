<%--
  Created by IntelliJ IDEA.
  User: No
  Date: 2021/6/1
  Time: 17:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <!-- 引入 layui.css -->
    <link rel="stylesheet" href="/static/tool/layui-v2.6.7/layui/css/layui.css" media="all">

    <!-- 引入 layui.js -->
    <script src="/static/tool/layui-v2.6.7/layui/layui.js"></script>

    <script src="/tool/jquery-1.7.2.js"></script>

    <style>
        input {
            height: 20px;
            width: 100%;
            margin-top: 30px;
            border: none;
            border-bottom: 1px solid #87a9f9
        }

        button {
            width: 280px;
            min-height: 20px;
            display: block;
            background-color: #4a77d4;
            border: 1px solid #3762bc;
            color: #fff;
            padding: 9px 14px;
            font-size: 15px;
            line-height: normal;
            border-radius: 5px;
            margin: 0;
        }
        .layui-container{
            padding-left: 60px;padding-right: 60px;padding-top:40px;border: 1px;
            position: fixed;    top: 0px;    left: 0px;    right: 0px;    bottom: 0px;margin: auto;
            width: 380px;height: 480px;background-color: #ffffff;text-align: center
        }
        .tp{
            position:relative;left:100px;bottom:30px;width: 25px;  height: 20px;
        }
    </style>
</head>
<body style="background-image: url(https://static.yximgs.com/udata/pkg/acfun/loginbg.be7a2d2876ab48ed.png)">
<div class="layui-container" style="box-shadow: 0 0 15px #c2c4c7;">
    <img src="/static/picture/img_2.png" style="width: 150px;height: 35px;">

    <input type="hidden" id="root" value="${sessionScope.login}">
    <div class="layui-tab layui-tab-brief" lay-filter="docDemoTabBrief">
        <ul class="layui-tab-title">
            <li class="layui-this" style="margin-left: 10%" id="user"><h3>学生登录</h3></li>
            <li id="admin"><h3>教师登录</h3></li>
        </ul>
    </div>
    <%
        String msg = (String) session.getAttribute("msg");
        String username = null;
        String password = null;
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie :cookies){
            if(cookie.getName().equals("username")){
                username = cookie.getValue();
            }
            else if(cookie.getName().equals("password")){
                password = cookie.getValue();
            }
        }
    %>
    <form method="post" class="form layui-form" id="form">
        <%--隐藏域--%>
        <input type="hidden" name="hidden" class="hidden" value="no">
        <input type="hidden" id="hidden" value="<%=msg==null?"":msg%>">
        <%--用户名--%>
        <input type="text" placeholder="用户名" name="username" id="username" value="<%=username==null?"":username%>" autocomplete="off">
        <a id="x" title="点击隐藏或显示密码">
            <img src="/static/picture/x.png" class="tp"></a>
        <%--密码--%>
        <input type="password" placeholder="密码" name="password" id="password" value="<%=password==null?"" :password%>" autocomplete="off">
        <a id="dj" title="点击隐藏或显示密码">
            <img src="/static/picture/close.png" class="tp" id="image"/></a>
        <div style="position:relative;bottom: 25px">
            <%--验证码栏和验证码图片--%>
            <div id="validatePanel layui-row" class="item">
                <input type="text" name="captcha" placeholder="请输入验证码" maxlength="4" style="width: 150px;" autocomplete="off">

                <canvas name="canvas" id="canvas" width="100" height="30px" class="validateImg"
                        style="padding-top: 28px"></canvas>

            </div>
            <%--保持登录复选框--%>
            <div style="padding-top: 18px">

                <div style="float:left;">
                    <img src="/static/picture/no.png" style="width: 15px;height: 15px" id="rem"
                         name="rem">
                    <span style="color: green">保持登录</span>
                </div>
                <a href="/register" style="float:right; color: green">注册</a><br/>
            </div>
            <div style="padding-top: 30px">
                <button id="submit" type="button" lay-submit lay-filter="login">登录</button>
            </div>
        </div>
    </form>
</div>
<script>
    layui.use(['form','jquery'], function () {
        var $ = layui.jquery,
            form = layui.form,
            layer = layui.layer,
            type = 'user';

        var hidden = $("#hidden").val();
        if (hidden !== "" && hidden != null) {
            alert(hidden)
        }
        var root = $("#root").val();
        if(root === "admin")
        {
            $("#admin").prop("class","layui-this");
            $("#user").prop("class","");
            type = 'admin'
        }
        if(root === "user"){
            $("#user").prop("class","layui-this");
            $("#admin").prop("class","");
            type = 'user'
        }
        //点击x图片时，设置用户名清空
        $("#x").click(function () {
            $("#username").val("");
        });
        //点击小眼睛图片时，改变图片，并且显示密码是否可见
        $("#dj").click(function () {
            if ($("#password").attr("type") === "text") {
                $("#password").prop("type", "password");
                $("#image").prop("src", "/picture/close.png");
            } else {
                $("#password").prop("type", "text");
                $("#image").prop("src", "/picture/on.png");
            }
        });
        $("#rem").click(function () {
            if ($("#rem").attr("src") === "/picture/no.png") {
                //this表示当前响应对象
                this.src = "/picture/ok.png";
                $(".hidden").val("yes");
            } else {
                this.src = "/picture/no.png";
                $(".hidden").val("no");
            }
        })

        $("#user").on('click',function (){
            type="user";
        })

        $("#admin").on('click',function (){
            type="admin";
        })

        //绘制验证码图像
        var show_num = [];
        draw(show_num);
        $("#canvas").on('click',function(){
            draw(show_num);
        })

        // 进行登录操作
        form.on('submit(login)', function (data) {
            data = data.field;
            if (data.username == '') {
                layer.msg('用户名不能为空');
                return false;
            }
            if (data.password == '') {
                layer.msg('密码不能为空');
                return false;
            }
            if(data.captcha == '') {
                layer.msg('验证码不能为空');
                return false;
            }
            if (data.captcha.toLowerCase() != show_num.join("")) {
                layer.msg('验证码不正确');
                return false;
            }

                $.post(
                    '<%=request.getContextPath()%>/tologin', //提交给谁处理
                    $("#form").serialize()+"&type="+type,//传递表单中所有组件的值
                    function (result) {//回调函数，根据返回结果进行相应的操作
                        if (result == "user") {
                            layer.msg('用户登录成功', {icon: 6, time: 1000}, function () {//使用Jquery弹层组件，自动关闭
                                window.location.href = '/index';
                            })
                        }
                        else if (result == "admin") {
                            layer.msg('管理员登录成功', {icon: 6, time: 1000}, function () {//使用Jquery弹层组件，自动关闭
                                window.location.href = '/main';
                            })
                        } else if (result == "error") {
                            layer.alert('用户名或密码错误', {icon: 2, title: '提示'}, function (index) {//使用Jquery弹层组件，点击确认按钮执行的代码
                                $("#username").focus();
                                $("#username").select();
                                layer.close(index);//关闭弹窗
                            });
                        }
                    });
            return false;
        });
    });

    window.draw=function(show_num){
        var $ = layui.jquery;
        var canvas_width=$('#canvas').width();
        var canvas_height=$('#canvas').height();
        var canvas = document.getElementById("canvas");//获取到canvas的对象，演员
        var context = canvas.getContext("2d");//获取到canvas画图的环境，演员表演的舞台
        canvas.width = canvas_width;
        canvas.height = canvas_height;
        var sCode = "A,B,C,E,F,G,H,J,K,L,M,N,P,Q,R,S,T,W,X,Y,Z,1,2,3,4,5,6,7,8,9,0";
        var aCode = sCode.split(",");
        var aLength = aCode.length;//获取到数组的长度

        for (var i = 0; i <= 3; i++) {
            var j = Math.floor(Math.random() * aLength);//获取到随机的索引值
            var deg = Math.random() * 30 * Math.PI / 180;//产生0~30之间的随机弧度
            var txt = aCode[j];//得到随机的一个内容
            show_num[i] = txt.toLowerCase();
            var x = 10 + i * 20;//文字在canvas上的x坐标
            var y = 20 + Math.random() * 8;//文字在canvas上的y坐标
            context.font = "bold 23px 微软雅黑";

            context.translate(x, y);
            context.rotate(deg);

            context.fillStyle = randomColor();
            context.fillText(txt, 0, 0);

            context.rotate(-deg);
            context.translate(-x, -y);
        }
        for (var i = 0; i <= 5; i++) {
            //验证码上显示线条
            context.strokeStyle = randomColor();
            context.beginPath();
            context.moveTo(Math.random() * canvas_width, Math.random() * canvas_height);
            context.lineTo(Math.random() * canvas_width, Math.random() * canvas_height);
            context.stroke();
        }
        for (var i = 0; i <= 30; i++) { //验证码上显示小点
            context.strokeStyle = randomColor();
            context.beginPath();
            var x = Math.random() * canvas_width;
            var y = Math.random() * canvas_height;
            context.moveTo(x, y);
            context.lineTo(x + 1, y + 1);
            context.stroke();
        }
    }

    window.randomColor=function() {//得到随机的颜色值
        var r = Math.floor(Math.random() * 256);
        var g = Math.floor(Math.random() * 256);
        var b = Math.floor(Math.random() * 256);
        return "rgb(" + r + "," + g + "," + b + ")";
    }
</script>
</body>
</html>
