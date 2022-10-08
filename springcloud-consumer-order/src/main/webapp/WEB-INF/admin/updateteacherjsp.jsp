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
</head>
<body scroll="no" style="overflow-x:hidden">
<div>
    <div style="width: 100%;height: 20px;padding: 20px;text-align: center">
        <h2>添加老师</h2>
    </div>
    <hr>
    <form class="layui-form" id="form" enctype="multipart/form-data" method="get">
        <div class="layui-row">
            <div class="layui-col-sm8">

                <div class="layui-col-sm11">
                    <input type="hidden" name="Id" id="Id" value="${teacher.id}">
                    <div class="layui-form-item" style="padding: 10px">
                        <label class="layui-form-label">姓名:</label>
                        <div class="layui-input-block">
                            <input name="Name" id="Name" lay-verify="required" class="layui-input" value="${teacher.name}">

                        </div>
                    </div>
                    <div class="layui-form-item" style="padding: 10px">
                        <label class="layui-form-label">密码:</label>
                        <div class="layui-input-block">
                            <input class="layui-input" id="Password" name="Password" lay-verify="required" value="${teacher.password}">
                        </div>
                    </div>
                    <div class="layui-form-item" style="padding: 10px">
                        <label class="layui-form-label">职称:</label>
                        <div class="layui-input-block" style="width: 380px">
                            <select id="Postion" name="Postion" >
                                <option>${teacher.postion}</option>
                                <option>讲师</option>
                                <option>助教</option>
                            </select>
                        </div>
                    </div>
                    <div class="layui-form-item" style="padding: 10px">
                        <label class="layui-form-label">邮箱:</label>
                        <div class="layui-input-block" style="width: 380px">
                            <input name="Email" id="Email" class="layui-input" lay-verify="required" value="${teacher.email}">
                        </div>
                    </div>

                    <div class="layui-form-item" style="padding: 10px">
                        <div class="layui-input-block">
                            <button type="button" class="layui-btn" lay-filter="ok" lay-submit="">提交</button>
                        </div>
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

        form.on('submit(ok)', function (data) {
            $.post(
                '${pageContext.request.contextPath}/updateteacher',//ajax请求
                $("#form").serialize(),//携带数据
                function (result) {//回调函数，根据返回结果进行相应的操作
                    if (result.code == 0) {
                        layer.msg('教师修改成功', {icon: 6, time: 1000}, function () {//使用Jquery弹层组件，自动关闭
                            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                            parent.layer.close(index); //再执行关闭
                        })
                    } else {
                        layer.msg('修改失败', {icon: 5, time: 1000}, function () {//使用Jquery弹层组件，自动关闭
                            $('#Topic').focus();//定位到menuName行
                        })
                    }
                }, 'json');
        });

    })
</script>
</body>
</html>
