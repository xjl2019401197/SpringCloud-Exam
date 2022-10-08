<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: No
  Date: 2021/5/12
  Time: 19:10
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

    <script src="/static/tool/jquery/jquery.js"></script>
    <style>
        .layui-form-item{
            width: 380px;padding: 10px
        }
    </style>
</head>
<body  >
<div style="padding: 50px">

    <form class="layui-form" id="form" enctype="multipart/form-data" method="get">
        <div class="layui-row">
            <div class="layui-col-sm5">


                <div class="layui-form-item" style="width: 380px;padding: 10px">
                    <label class="layui-form-label">试卷名称:</label>
                    <div class="layui-input-block">
                        <input name="Examname" id="Examname" class="layui-input" lay-verify="required" autofocus placeholder="试卷名称">
                    </div>
                </div>
                <div class="layui-form-item" style="width: 380px;padding: 10px">
                    <label class="layui-form-label">命题人:</label>
                    <div class="layui-input-block">
                        <select id="teacher" name="teacher">
                            <c:forEach items="${list}" var="teacher">
                                <option value="${teacher.name}">${teacher.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item" style="width: 380px;padding: 10px">
                    <label class="layui-form-label">设置口令:</label>
                    <div class="layui-input-block" style="width: 380px">
                        <input name="Password" id="Password"  class="layui-input"  placeholder="考试密码，不设置则为开放试卷">
                    </div>
                </div>
                <div class="layui-form-item" style="padding: 10px">
                    <label class="layui-form-label">难度:</label>
                    <div class="layui-input-block" style="width: 380px">
                        <select id="Difficulty" name="Difficulty">
                            <option disabled>--请选择--</option>
                            <option value="简单">简单</option>
                            <option value="普通">普通</option>
                            <option value="困难">困难</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item" style="padding: 10px; ">
                    <label class="layui-form-label">选择题数量:</label>
                    <div class="layui-input-block" style="width: 380px">
                        <input name="Selnum" id="Selnum"  class="layui-input" lay-verify="required" placeholder="选择题数量">
                    </div>
                </div>
                <div class="layui-form-item" style="padding: 10px; ">
                    <label class="layui-form-label">每小题分数:</label>
                    <div class="layui-input-block" style="width: 380px">
                        <input name="Selscore" id="Selscore"  class="layui-input" lay-verify="required" placeholder="选择题分数">
                    </div>
                </div>
                <div class="layui-form-item" style="padding: 10px; ">
                    <label class="layui-form-label">判断题数量:</label>
                    <div class="layui-input-block" style="width: 380px">
                        <input name="Judnum" id="Judnum"  class="layui-input" lay-verify="required" placeholder="判断题数量" >
                    </div>
                </div>
                <div class="layui-form-item" style="padding: 10px; ">
                    <label class="layui-form-label">每小题分数:</label>
                    <div class="layui-input-block" style="width: 380px">
                        <input name="Judscore" id="Judscore"  class="layui-input" lay-verify="required" placeholder="判断题分数">
                    </div>
                </div>
                <div class="layui-form-item" style="padding: 10px; ">
                    <label class="layui-form-label">多选题数量:</label>
                    <div class="layui-input-block" style="width: 380px">
                        <input name="Multiplenum" id="Multiplenum"  class="layui-input" lay-verify="required" placeholder="多选题数量">
                    </div>
                </div>
                <div class="layui-form-item" style="padding: 10px; ">
                    <label class="layui-form-label">每小题分数:</label>
                    <div class="layui-input-block" style="width: 380px">
                        <input name="Multiplescore" id="Multiplescore"  class="layui-input" lay-verify="required" placeholder="多选题分数">
                    </div>
                </div>
                <%
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String now = simpleDateFormat.format(new Date()).replace(" ","T");
                %>
                <div class="layui-form-item" style="padding: 10px; ">
                    <label class="layui-form-label">考试日期</label>
                    <div class="layui-input-block" style="width: 600px">
                        <input type="datetime-local"  value="<%=now%>" name="begindate"/>
                        至
                        <input type="datetime-local" value="<%=now%>" name="enddate"/>
                    </div>
                </div>
                <div class="layui-form-item" style="padding: 10px; ">
                    <label for="examtime" class="layui-form-label">考试时长</label>
                    <div class="layui-input-block" style="width: 380px">
                        <input class="layui-input" id="examtime" name="examtime"  placeholder="当前试卷考试时长 单位(min)" />
                    </div>
                </div>
                <br />

                <div class="layui-form-item" style="padding: 10px">
                    <div class="layui-input-block">
                        <button type="button" class="layui-btn" lay-filter="ok" lay-submit="">添加考试</button>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>
<script>
    layui.use(['form', 'laydate', 'layer', 'upload'], function () {
        var form = layui.form,
            layer = layui.layer,
            $ = layui.jquery;

        layui.use('form', function() {
            var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功

            //……

            //但是，如果你的HTML是动态生成的，自动渲染就会失效
            //因此你需要在相应的地方，执行下述方法来进行渲染
            $("#Difficulty").val("简单")
            form.render();
            form.render('select'); //刷新select选择框渲染

        });
        form.on('submit(ok)', function (data) {
            $.post(
                '${pageContext.request.contextPath}/insertexam',//ajax请求
                $("#form").serialize(),//携带数据
                function (result) {//回调函数，根据返回结果进行相应的操作
                    if (result.code == 0) {
                        layer.msg('考试添加成功', {icon: 6, time: 1000}, function () {//使用Jquery弹层组件，自动关闭
                            window.location = "examinfo"
                        })
                    } else {
                        layer.msg('考试添加失败', {icon: 5, time: 1000}, function () {//使用Jquery弹层组件，自动关闭
                            $('#Examname').focus();//定位到menuName行
                        })
                    }
                }, 'json');
        });

    })
</script>
</body>
</html>
