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
        <h2>添加题目</h2>
    </div>
    <hr>
    <form class="layui-form" id="form" enctype="multipart/form-data" method="post">
        <div class="layui-row">
            <div class="layui-col-sm8">

                <div class="layui-col-sm11">
                    <div class="layui-form-item"style="padding: 10px">
                        <label class="layui-form-label">题目:</label>
                        <div class="layui-input-block">
                            <textarea rows="2" cols="50"  lay-verify="required" 	autofocus  id="Topic" name="Topic"></textarea>
<%--                            <input type="text" name="menuName" id="menuName" placeholder="请输入菜名" class="layui-input"--%>
<%--                                   lay-verify="required">--%>
                        </div>
                    </div>
                    <div class="layui-form-item"style="padding: 10px">
                        <label class="layui-form-label">选项A:</label>
                        <textarea rows="2" cols="50"  lay-verify="required" id="A" name="A"></textarea>
                    </div>
                    <div class="layui-form-item"style="padding: 10px">
                        <label class="layui-form-label">选项B:</label>
                        <textarea rows="2" cols="50"  lay-verify="required" id="B" name="B"></textarea>
                    </div>
                    <div class="layui-form-item" style="padding: 10px">
                        <label class="layui-form-label">选项C:</label>
                        <textarea rows="2" cols="50"  lay-verify="required" id="C" name="C"></textarea>
                    </div>
                    <div class="layui-form-item" style="padding: 10px">
                        <label class="layui-form-label">选项D:</label>
                        <textarea rows="2" cols="50"  lay-verify="required" id="D" name="D"></textarea>
                    </div>

                    <div class="layui-form-item" style="padding: 10px">
                        <label class="layui-form-label">答案:</label>
                        <div class="layui-input-block" style="width: 380px">
                            <select id="Answer" name="Answer">
                                <option disabled>--请选择--</option>
                                <option>A</option>
                                <option>B</option>
                                <option>C</option>
                                <option>D</option>
                            </select>
                        </div>
                    </div>
                    <input type="hidden" name="Type" value="选择题">
                    <div class="layui-form-item" style="padding: 10px">
                        <label class="layui-form-label">难度:</label>
                        <div class="layui-input-block" style="width: 380px">
                            <select id="Difficulty" name="Difficulty">
                                <option disabled>--请选择--</option>
                                <option>简单</option>
                                <option>普通</option>
                                <option>困难</option>
                            </select>
                        </div>
                    </div>

                    <div class="layui-form-item" style="padding: 10px">
                        <div class="layui-input-block">
                            <button type="button" class="layui-btn" lay-filter="ok" lay-submit="">添加</button>
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
                '${pageContext.request.contextPath}/insertselect',//ajax请求
                $("#form").serialize(),//携带数据
                function (result) {//回调函数，根据返回结果进行相应的操作
                    if (result.code==0) {
                        layer.msg('题目添加成功', {icon: 6, time: 1000}, function () {//使用Jquery弹层组件，自动关闭
                            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                            parent.layer.close(index); //再执行关闭
                        })
                    }
                    else{
                        layer.msg( '题目已存在', {icon: 5, time: 1000}, function () {//使用Jquery弹层组件，自动关闭
                            $('#Topic').focus();//定位到menuName行
                        })
                    }
                },'json');
        });

    })
</script>
</body>
</html>
