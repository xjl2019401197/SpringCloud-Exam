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
    <!-- 引入 layui.css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/tool/layui-v2.6.7/layui/css/layui.css" media="all">

    <!-- 引入 layui.js -->
    <script src="${pageContext.request.contextPath}/static/tool/layui-v2.6.7/layui/layui.js"></script>

    <script src="${pageContext.request.contextPath}/tool/jquery-1.7.2.js"></script>


    <style>
        input {
            height: 20px;
            width: 100%;
            margin-top: 20px;
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

        .layui-container {
            padding-left: 60px;
            padding-right: 60px;
            padding-top: 40px;
            border: 1px;
            position: fixed;
            top: 0px;
            left: 0px;
            right: 0px;
            bottom: 0px;
            margin: auto;
            width: 1000px;
            height: 550px;
            background-color: #ffffff;
            text-align: center
        }

        button {
            width: 100%;
            min-height: 20px;
            display: block;
            background-color: #00c1de;
            border: 1px solid #3762bc;
            color: #fff;
            padding: 9px 14px;
            font-size: 15px;
            line-height: normal;
            border-radius: 5px;
            margin: 0;
        }
    </style>

</head>
<body style="background-color: #EBEEF3">
<div class="layui-container" style="box-shadow: 0 0 15px #c2c4c7;">
    <div class="layui-row">
        <div style="float: left;margin-left: 350px"><h1>欢迎注册</h1></div>
        <div style="float: right;"><font color="#acafb1">已有账号,</font> <a
                href="${pageContext.request.contextPath}/login">快捷登录</a></div>
    </div>
    <div style="margin-left: 250px;margin-right: 300px">
        <form method="post" class="form layui-form" id="form">
            <select class="" style="width: 100px" lay-filter="change">
                <option value="1">用户</option>
                <option value="2" id="admin">管理员</option>
            </select>
            <input type="text" placeholder="请输入用户名" name="name" id="username" class="layui-input" autocomplete="off">
            <input type="text" placeholder="请输入密码" name="password" id="password" class="layui-input" autocomplete="off">
            <input type="text" placeholder="再次输入密码" name="rep" id="rep" class="layui-input" autocomplete="off">
            <div id="validatePanel layui-row" class="item">
                <input type="text" name="captcha" placeholder="请输入验证码" maxlength="4" style="width: 200px;"
                       class="layui-input"autocomplete="off">

                <canvas name="canvas" id="canvas" width="100" height="30px" class="validateImg"
                        style="position:relative;left:110px;bottom:30px;"></canvas>

            </div>
            <div>
                <button id="submit" type="button" lay-submit lay-filter="login">注册</button>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
        $(function (){
            var patt=/^\w{4,10}$/ ;
            $("#password").blur(function (){
                var password = $("#password").val();
                if(password !== "")
                    if(!patt.test(password)){
                        layer.msg("密码不合法")
                    }
            })
        })
    layui.use(['form', 'jquery'], function () {
        var $ = layui.jquery,
            form = layui.form,
            layer = layui.layer,
            type = 'user';


        //绘制验证码图像
        var show_num = [];
        draw(show_num);
        $("#canvas").on('click', function () {
            draw(show_num);
        })



        form.on('select(change)',function (data){
            if(data.value == 1)type = "user"
            else  type ="admin"
        })
        // 进行登录操作
        form.on('submit(login)', function (data) {
            data = data.field;


            if (data.username === '') {
                layer.msg('用户名不能为空');
                return false;
            }
            if (data.password === '') {
                layer.msg('密码不能为空');
                return false;
            }
            if (data.rep === '') {
                layer.msg("确定密码不能为空");
                return false;
            }
            if (data.password !== data.rep) {
                layer.msg("两次密码输入不一致");
                return false;
            }
            if (data.captcha === '') {
                layer.msg('验证码不能为空');
                return false;
            }
            if (data.captcha.toLowerCase() !== show_num.join("")) {
                layer.msg('验证码不正确');
                return false;
            }

            $.post(
                '${pageContext.request.contextPath}/register', //提交给谁处理
                $("#form").serialize() + "&level=普通用户&type=" + type,//传递表单中所有组件的值
                function (result) {//回调函数，根据返回结果进行相应的操作
                    if (result == "ok") {
                        layer.msg('注册成功', {icon: 6, time: 1000}, function () {//使用Jquery弹层组件，自动关闭
                            window.location.href = '${pageContext.request.contextPath}/login';
                        })
                    } else if (result == "false") {
                        layer.alert('用户名已存在', {icon: 2, title: '提示'}, function (index) {//使用Jquery弹层组件，点击确认按钮执行的代码
                            $("#username").focus();
                            $("#username").select();
                            layer.close(index);//关闭弹窗
                        });
                    }
                });
            return false;
        });
    });
    window.draw = function (show_num) {
        var $ = layui.jquery;
        var canvas_width = $('#canvas').width();
        var canvas_height = $('#canvas').height();
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

    window.randomColor = function () {//得到随机的颜色值
        var r = Math.floor(Math.random() * 256);
        var g = Math.floor(Math.random() * 256);
        var b = Math.floor(Math.random() * 256);
        return "rgb(" + r + "," + g + "," + b + ")";
    }
</script>
</body>
</html>
