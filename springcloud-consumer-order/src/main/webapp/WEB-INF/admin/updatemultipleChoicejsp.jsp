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
<div >
    <div style="width: 100%;height: 20px;padding: 20px;text-align: center">
        <h2>编辑</h2>
    </div>
    <hr>
    <form class="layui-form" id="form" enctype="multipart/form-data" method="get">
        <div class="layui-row">
            <div class="layui-col-sm8">
                <input type="hidden" name="id" class="hidden" value="${item.id}">
                <input type="hidden" name="type" class="hidden" value="${item.type}">
                <div class="layui-col-sm11">
                    <div class="layui-form-item"style="padding: 10px">
                        <label class="layui-form-label">题目:</label>
                        <div class="layui-input-block">
                            <textarea rows="2" cols="50"  lay-verify="required" 	autofocus  id="Topic" name="Topic" >${item.topic}</textarea>
                            <%--                            <input type="text" name="menuName" id="menuName" placeholder="请输入菜名" class="layui-input"--%>
                            <%--                                   lay-verify="required">--%>
                        </div>
                    </div>
                    <div class="layui-form-item"style="padding: 10px">
                        <label class="layui-form-label">选项A:</label>
                        <textarea rows="2" cols="50"  lay-verify="required" id="A" name="A">${item.a}</textarea>
                    </div>
                    <div class="layui-form-item"style="padding: 10px">
                        <label class="layui-form-label">选项B:</label>
                        <textarea rows="2" cols="50"  lay-verify="required" id="B" name="B">${item.b}</textarea>
                    </div>
                    <div class="layui-form-item" style="padding: 10px">
                        <label class="layui-form-label">选项C:</label>
                        <textarea rows="2" cols="50"  lay-verify="required" id="C" name="C">${item.c}</textarea>
                    </div>
                    <div class="layui-form-item" style="padding: 10px">
                        <label class="layui-form-label">选项D:</label>
                        <textarea rows="2" cols="50"  lay-verify="required" id="D" name="D">${item.d}</textarea>
                    </div>

                    <div class="layui-form-item" style="padding: 10px">
                        <label class="layui-form-label">答案:</label>
                        <div class="layui-input-block" style="width: 380px">
                            <span style="margin-left: 20px;margin-right: 10px">A</span> <input type="checkbox" name="Answer" checked="checked"  value="A">
                            <span style="margin-left: 20px;margin-right: 10px">B</span> <input type="checkbox" name="Answer"  value="B">
                            <span style="margin-left: 20px;margin-right: 10px">C</span> <input type="checkbox" name="Answer"  VALUE="C">
                            <span style="margin-left: 20px;margin-right: 10px">D</span> <input type="checkbox" name="Answer"  value="D">
                        </div>
                    </div>

                    <div class="layui-form-item" style="padding: 10px">
                        <label class="layui-form-label">难度:</label>
                        <div class="layui-input-block" style="width: 380px">
                            <select id="Difficulty" name="Difficulty" >
                                <option>${item.difficulty}</option>
                                <option value="简单">简单</option>
                                <option value="普通">普通</option>
                                <option value="困难">困难</option>
                            </select>
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
    layui.use(['form','laydate', 'layer','upload'], function () {
        var form = layui.form,
            layer = layui.layer,
            $ = layui.jquery;
        form.on('submit(ok)', function (data) {
            $.post(
                '${pageContext.request.contextPath}/updatemultipleChoice',//ajax请求
                $("#form").serialize(),//携带数据
                function (result) {//回调函数，根据返回结果进行相应的操作
                    if (result.code==0) {
                        layer.msg('修改成功', {icon: 6, time: 1000}, function () {//使用Jquery弹层组件，自动关闭
                            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                            parent.layer.close(index); //再执行关闭
                        })
                    }
                    else{
                        layer.msg( '修改失败', {icon: 5, time: 1000}, function () {//使用Jquery弹层组件，自动关闭
                            $('#Topic').focus();//定位到menuName行
                        })
                    }
                },'json');
        });

    })
</script>
</body>
</html>
