<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <button class="btn btn-outline btn-primary" data-toggle="modal" data-target="#myModa_n">添加</button>
    <form style="margin:0px;display:inline;"  action="<%=basePath%>/Uploadstudent" method="post" enctype="multipart/form-data">
        <input  class="" style="display: inline" id="file" type="file" name="file"/>
        <input style="display: inline ; width: 5%" type="submit" name="submit" id="submit" value="上传" />
    </form>
    <table border="1" class="table table-bordered" id="tab1" style="text-align: center">
        <thead>
        <tr>
            <th colspan="8" style="text-align:center;font-size: 20px ">学生信息管理</th>
        </tr>
        </thead>
        <tr>
            <th style="text-align: center">学号</th>
            <th style="text-align: center">姓名</th>
            <th style="text-align: center">密码</th>
            <th style="text-align: center">专业</th>
            <th style="text-align: center">入学时间</th>
            <th style="text-align: center">操作</th>
        </tr>
        <c:forEach items="${list}" var="student">
            <tr>
                <td>${student.stuid}</td>
                <td>${student.name}</td>
                <td>${student.password}</td>
                <td>${student.major}</td>
                <td>${student.admtime}</td>
                <td>
                        <%--
                                            <input class="btn btn-outline btn-primary" type="button" value="查看详情" onclick="trash(${exam.id})">
                        --%>
                    <input class="btn btn-outline btn-primary" type="button" value="编辑" onclick="editTr(this)"
                          >
                    <input class="btn btn-outline btn-primary" type="button" value="删除" onclick="trash(${student.stuid})"
                           data-toggle="modal" data-target="#trashModal">
                </td>
            </tr>
        </c:forEach>
    </table>
    <br>
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
                <strong>你将删除此学生的信息！</strong>
            </div>
            <!-- 模糊框底部 -->
            <div class="modal-footer">
                <button type="button" class="delSure btn btn-info" data-dismiss="modal">确定</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div>

</div>
<!-- 编辑的模态框 -->
<div class="modal fade" id="stuedit" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="Label">编辑</h4>
            </div>
            <form class="form-horizontal" id="form">
                <div class="modal-body">
                    <input name="Stuid" id="Stuid" hidden="hidden"/>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">姓名</label>
                        <div class="col-sm-10">
                            <input type="text" name="Name" class="form-control" id="Name" placeholder="xjl">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">密码</label>
                        <div class="col-sm-10">
                            <input type="text" name="Password" class="form-control" id="Password"
                                   placeholder="123">
                            <span class="help-block"></span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">专业</label>
                        <div class="col-sm-10">
<%--                            <input type="text" name="" class="form-control" id="Password"--%>
<%--                                   placeholder="123">--%>
                    <select class="form-control" name="Major" id="Major">
                        <option selected="selected">软件学院</option>
                        <option>外国语</option>
                        <option>土木建设</option>
                    </select>
                            <span class="help-block"></span>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="submit" class="btn btn-primary" id="editbtn">保存</button>
                </div>
            </form>
        </div>
    </div>
</div>
<%--
添加
--%>
<div>
    <tr class="append-row">
        <td colspan="5" align="right">
            <!-- Modal -->
            <div class="modal fade" id="myModa_n" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabe2" style="text-align:center">添加选项</h4>
                        </div>
                        <form action="<%=basePath%>/addstudent" method="post">
                            <div class="modal-body" style="text-align:left">
                                <div class="form-group input-group">
                                    <span class="input-group-addon" style="height: 40px ; tab-size: 16px">请输入学号：</span>
                                    <input type="text" style="height: 40px " class="form-control" name="Stuid">
                                </div>
                                <div class="form-group input-group">
                                    <span class="input-group-addon" style="height: 40px ; tab-size: 16px">请输入姓名：</span>
                                    <input type="text" style="height: 40px" class="form-control" name="Name">
                                </div>
                                <div class="form-group input-group">
                                    <span class="input-group-addon" style="height: 40px ; tab-size: 16px">请输入密码：</span>
                                    <input type="text" style="height: 40px" class="form-control" name="Password">
                                </div>
                                <div class="form-group input-group">
                                    <span class="input-group-addon" style="height: 40px ; tab-size: 16px">专业：</span>
                                    <select class="form-control" name="Major" style="height: 40px ;" >
                                        <option selected="selected">软件学院</option>
                                        <option>外国语</option>
                                        <option>土木建设</option>
                                    </select>
                                </div>
                                <div class="form-group input-group">
                                    <span class="input-group-addon" style="height: 40px ; tab-size: 16px">入学时间：</span>
                                    <input type="datetime-local"  value="2021-12-19 16:58:00" name="Admtime" style="height: 40px ;" />
                                </div>
                            </div>
                            <div class="modal-footer">
                                <button type="button"  class="btn btn-outline btn-primary" data-dismiss="modal">关闭</button>
                                <button type="submit"  class="btn btn-outline btn-primary" id="add">添加</button>
                            </div>
                        </form>
                    </div>
                    <!-- /.modal-content -->
                </div>
                <!-- /.modal-dialog -->
            </div>
        </td>
    </tr>
</div>


<script src="/static/tool/jquery/jquery.js"></script>

<script src="/static/tool/bootstrap/bootstrap.js"></script>

<script src="/static/tool/metisMenu/metisMenu.js"></script>

<script src="/static/tool/dist/sb-admin-2.js"></script>
</body>
<script>
    //删除
    function trash(Stuid) {
        if (!Stuid) {
            alert("error");
        } else {
            $(".delSure").click(function () {
                $.post({
                    url: '<%=basePath%>/deletestudent?Stuid=' + Stuid,
                    type: 'POST',
                    success: function (data) {
                        $("body").html(data);
                    }
                });
            });
        }
    }


    // 编辑信息的方法
    function editTr(obj) {
        var Stuid=$(obj).parent().parent().children("td").get(0).innerHTML;
        var name=$(obj).parent().parent().children("td").get(1).innerHTML;
        var psw=$(obj).parent().parent().children("td").get(2).innerHTML;
        var major=$(obj).parent().parent().children("td").get(3).innerHTML;
        //alert("num = " + num + "name = " + name +"psw = " + psw);
        $("#Stuid").val(Stuid);
        $("#Name").val(name);
        $("#Password").val(psw);
        $("#Major").val(major);
        $("#stuedit").modal('show');
    }
    $("#editbtn").click(function(){
        $.post({
            url:"<%=basePath%>/updatestudent",
            type:"POST",
            data:$("#form").serialize(),
            success:function(data) {
                $("#stuedit").modal('hide');
                location.reload();
            }
        });
    });
        $("#semester").change(function(){
            var seid=$(this).val();
            $.ajax({
                type:"POST",
                cache:false,
                url:"<%=request.getContextPath()%>/basic/findmaall.do",
                data:{"seid":seid},
                dataType:"json",
                success:function(data){
                    $("#major option").remove();
                    $("#class option").remove();
                    $("#class").append("<option>" + "--班级选择--"+ "</option>");
                    $("#major").append("<option>" + "--专业选择--"+ "</option>");
                    for (var i = 0; i < data.length; i++) {
                        $("#major").append("<option value='" + data[i].maid + "'>" + data[i].maname + "</option>");
                    }
                }
            });
        });
        $("#major").change(function(){
            var maid=$(this).val();
            $.ajax({
                type:"POST",
                cache:false,
                url:"<%=request.getContextPath()%>/basic/findciall.do",
                data:{"maid":maid},
                dataType:"json",
                success:function(data){
                    //追加本机option前，先清除city和county的option，以免重选时干扰
                    $("#class option").remove();
                    $("#class").append("<option>" + "--班级选择--"+ "</option>");
                    for (var i = 0; i < data.length; i++) {
                        $("#class").append("<option value='" + data[i].cid + "'>" + data[i].cname + "</option>");
                    }
                }
            });
        });

</script>
</html>
