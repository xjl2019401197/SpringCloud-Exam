<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: No
  Date: 2021/12/19
  Time: 14:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
    %>
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
</head>
<body>
<div id="wrapper">
    <br>
    <br>
    <table border="1" class="table table-bordered" id="tab1" style="text-align: center">
        <thead>
        <tr>
            <th colspan="8" style="text-align:center;font-size: 20px ">考试基础信息设置</th>
        </tr>
        </thead>
        <tr>
            <th style="text-align: center">考试名称</th>
            <th style="text-align: center">考试时间</th>
            <th style="text-align: center">考试时长</th>
            <th style="text-align: center">密码</th>
            <th style="text-align: center">状态</th>
            <th style="text-align: center">命题人</th>
            <th style="text-align: center">操作</th>
        </tr>
        <c:forEach items="${list}" var="exam">
            <tr>
                <td>${exam.examname}</td>
                <td>${exam.begindate}-${exam.enddate}</td>
                <td>${exam.examtime}</td>
                <c:choose>
                    <c:when test="${empty exam.password}">
                        <td>无</td>
                    </c:when>
                    <c:otherwise>
                        <td>${exam.password}</td>
                    </c:otherwise>
                </c:choose>
                <%
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String now = sdf.format(new Date());
                    now = now.replace(' ','T');
                    request.setAttribute("currentTime", now);


                %>
                <c:choose>
                    <c:when test="${exam.enddate < currentTime}">
                        <td><font color="red">已结束</font></td>
                    </c:when>
                    <c:when test="${exam.begindate >currentTime }">
                        <td><font color="blue">未开始</font></td>
                    </c:when>
                    <c:otherwise>
                        <td><font color="green">进行中</font></td>
                    </c:otherwise>
                </c:choose>
                <td>${exam.teacher}</td>
                <td>
<%--
                    <input class="btn btn-outline btn-primary" type="button" value="查看详情" onclick="trash(${exam.id})">
--%>
                        <input  class="btn btn-outline btn-primary" type="button" value="编辑"  onclick="edit(${exam.id})" data-toggle="modal" data-target="#myModa_n" >
                    <input class="btn btn-outline btn-primary" type="button" value="删除" onclick="trash(${exam.id})" data-toggle="modal" data-target="#trashModal">
                </td>
            </tr>
        </c:forEach>
    </table>
    <br>
</div>
<!-- 更改模态框  data-toggle="modal" data-target="#myModa_n" -->
<div class="modal fade" id="myModa_n" tabindex="-1" role="dialog"  aria-hidden="true">
    <div class="modal-dialog">
        <form action="<%=basePath%>/updateexam" method="post">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">考试信息更改</h4>
                </div>
                <div class="modal-body" style="text-align:left">
                    <input name="id" id="id" hidden="hidden" />
                    <input name="difficulty" id="difficulty" hidden="hidden" />
                    <div class="form-group input-group">
                        <span class="input-group-addon" style="height: 40px ; tab-size: 16px">请输入考试名称：</span>
                        <input type="text" style="height: 40px" class="form-control" id="examname"
                               name="examname">
                    </div>
                    <div class="form-group has-success">
                        <label>请选择更改后的考试开始时间:</label>
                        <div>
                            <input style="width: 45%" type="datetime-local" id="begindate" name="begindate" />
                        </div>

                        <label>请选择更改后的考试结束时间:</label>
                        <div>
                            <input style="width: 45%"  type="datetime-local" id="enddate" name="enddate"/>
                        </div>
                    </div>
                    <div class="form-group input-group">
                        <span class="input-group-addon" style="height: 40px ; tab-size: 16px">请输入考试密码：</span>
                        <input type="text" style="height: 40px" class="form-control" id="password"
                               name="password">
                    </div>
                    <div class="form-group input-group">
                        <span class="input-group-addon" style="height: 40px ; tab-size: 16px">选择题题数：</span>
                        <input type="text" style="height: 40px" class="form-control" id="selnum"
                               name="selnum">
                    </div>
                    <div class="form-group input-group">
                        <span class="input-group-addon" style="height: 40px ; tab-size: 16px">每小题分数：</span>
                        <input type="text" style="height: 40px" class="form-control" id="selscore"
                               name="selscore">
                    </div>
                    <div class="form-group input-group">
                        <span class="input-group-addon" style="height: 40px ; tab-size: 16px">判断题题数：</span>
                        <input type="text" style="height: 40px" class="form-control" id="judnum"
                               name="judnum">
                    </div>
                    <div class="form-group input-group">
                        <span class="input-group-addon" style="height: 40px ; tab-size: 16px">每小题分数：</span>
                        <input type="text" style="height: 40px" class="form-control" id="judscore"
                               name="judscore">
                    </div>
                    <div class="form-group input-group">
                        <span class="input-group-addon" style="height: 40px ; tab-size: 16px">多选题题数：</span>
                        <input type="text" style="height: 40px" class="form-control" id="multiplenum"
                               name="multiplenum">
                    </div>
                    <div class="form-group input-group">
                        <span class="input-group-addon" style="height: 40px ; tab-size: 16px">每小题分数：</span>
                        <input type="text" style="height: 40px" class="form-control" id="multiplescore"
                               name="multiplescore">
                    </div>
                    <div class="form-group input-group">
                        <span class="input-group-addon" style="height: 40px ; tab-size: 16px">请输入考试时长：</span>
                        <input type="text" style="height: 40px" class="form-control" id="examtime"
                               name="examtime">
                    </div>
                    <div class="form-group">
                        <label>请选择考试命题人</label>
                        <select class="form-control" id="teacher" name="teacher">
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-outline btn-primary" data-dismiss="modal">关闭</button>
                    <button type="submit" class="btn btn-outline btn-primary">确定</button>
                </div>
            </div>
        </form>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
<!-- 删除的模态框 -->
<div class="modal fade" id="trashModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <!-- 模糊框头部 -->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;
                </button>
                <h4 class="modal-title">删除！</h4>
            </div>
            <!-- 模糊框主体 -->
            <div class="modal-body">
                <strong>你将删除此次考试的全部信息！</strong>
            </div>
            <!-- 模糊框底部 -->
            <div class="modal-footer">
                <button type="button" class="delSure btn btn-info" data-dismiss="modal">确定</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>

</div>
<script src="/static/tool/jquery/jquery.js"></script>

<script src="/static/tool/bootstrap/bootstrap.js"></script>

<script src="/static/tool/metisMenu/metisMenu.js"></script>

<script src="/static/tool/dist/sb-admin-2.js"></script>
</body>
<script>

    //删除
    function trash(id) {
        if (!id) {
            alert("error");
        } else {
            $(".delSure").click(function () {
                $.ajax({
                    url: '<%=basePath%>/deleteexam?id=' + id,
                    type: 'POST',
                    success: function (data) {
                        $("body").html(data);
                    }
                });
            });
        }
    }

    function edit(id) {
        $.ajax({
            url: '<%=basePath%>/getexambyid?id='+id,
            type: 'Get',
            dataType: 'json',
            contentType: 'application/json;charset=UTF-8',
            success: function (data) {
                console.log("数据" + data.examname);
                $("#id").val(data.id)
                $("#examname").val(data.examname);
                $("#password").val(data.password);
                $("#difficulty").val(data.difficulty);
                $("#begindate").val(data.begindate.replace(" ", "T"));
                $("#enddate").val(data.enddate.replace(" ","T"));
                $("#selnum").val(data.selnum);
                $("#selscore").val(data.selscore);
                $("#judnum").val(data.judnum);
                $("#judscore").val(data.judscore);
                $("#multiplenum").val(data.multiplenum);
                $("#multiplescore").val(data.multiplescore);
                $("#examtime").val(data.examtime);
                $("#state").val(data.state);
                $("#teacher").append("<option value='"+data.teacher+"'>"+data.teacher+"</option>")
                $("#editexam").modal('show');
            },
            error: function () {
                alert("错误");
            }
        });
        $("#editexam").modal('show');
    }

</script>
</html>
