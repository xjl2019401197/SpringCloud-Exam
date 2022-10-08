<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
%><!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">

    <title>Title</title>
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

    <style type="text/css">
        .opt {
            width: 30px;
            height: 30px;
            border: 1px solid gray;
            float: left;

        }

        .opt_later {
            width: 30px;
            height: 30px;
            border: 1px solid gray;
            float: left;
            background-color: gray;
        }
    </style>
</head>

<body>
<div id="wrapper">
    <div>
        <table border="0" width="90%" cellpadding="0" style="min-width:860px;">
            <tr>
                <!-- left -->
                <td valign="top">
                    <div class="tm_paper">
                        <div class="tm_paper_head">
                            <h5 style="background:#a6b4cd; color: #ffffff; padding:15px 0; font-size:14px; font-weight:bold; text-align: center">
                                ${exam.examname}
                            </h5>
                            <h5>
                                <b>开考时间</b> :
                                ${exam.begindate}-${exam.enddate}
                                &nbsp;&nbsp;
                                <b>考试时长</b> :${exam.examtime}分钟
                                &nbsp;&nbsp;
                                <b>选择题每小题分数</b> : ${exam.selscore}
                                &nbsp;&nbsp;
                                <b>判断题每小题分数</b> : ${exam.judscore}
                                &nbsp;&nbsp;
                                <b>出卷人</b> : ${exam.teacher}
                            </h5>
                            <h5 style="background:#a6b4cd; color: #ffffff; padding:5px 0; font-size:14px; font-weight:bold">
                                考生信息
                            </h5>
                            <h5 style="margin-top: 10px">
                                <b>考生学号</b> :
                                ${student.stuid}
                                &nbsp;&nbsp;
                                <b>考生姓名</b> :
                                ${student.name}
                                &nbsp;&nbsp;
                                &nbsp;<span style="color: red">
                                <b>得分:</b>
                                &nbsp;${score}
                            </span>
                            </h5>
                        </div>

                    </div>
                </td>
            </tr>
        </table>
    </div>
    <%
        int selindex = 0;
        int judindex = 0;
        int mulindex = 0;
    %>

    <div class="container">
        <div class="row">
            <div class="col-sm-8" style="top: 20px">
                <h3>一、单选题（每题${exam.selscore}分）</h3>
                <c:forEach items="${choicelist}" var="ch" varStatus="status">
                    <form class="layui-form">
                        <fieldset>
                            <div>
                                <h3 style="margin-top: 10px;margin-bottom: 10px"><%=++selindex%>.${ch.topic}</h3>
                            </div>
                            <input class="layui-form-radio" type="radio" name="Select${status.index}"
                                   value="A"/>A: ${ch.a}<br/>
                            <input class="layui-form-radio" type="radio" name="Select${status.index}"
                                   value="B"/>B: ${ch.b}<br/>
                            <input class="layui-form-radio" type="radio" name="Select${status.index}"
                                   value="C"/>C: ${ch.c}<br/>
                            <input class="layui-form-radio" type="radio" name="Select${status.index}"
                                   value="D"/>D: ${ch.d}<br/>
                            <br/>
                            <div style="margin-bottom: 20px;font-size: larger">正确答案: <span name="Select${status.index}"
                                                                                           style="color: green;"></span>
                            </div>
                        </fieldset>
                    </form>
                </c:forEach>

                <h3>二、判断题（每题${exam.judscore}分）</h3>
                ${judan}
                <c:forEach items="${judgelist}" var="ju" varStatus="status">
                    <form class="layui-form">
                        <fieldset>
                            <div>
                                <h3 style="margin-top: 10px;margin-bottom: 10px"><%=++judindex%>.${ju.content}</h3>
                            </div>
                            <input class="layui-form-radio" type="radio" name="Judge${status.index}" value="正确"/>正确<br/>
                            <input class="layui-form-radio" type="radio" name="Judge${status.index}" value="错误"/>错误<br/>
                            <br/>
                            <div style="margin-bottom: 20px;font-size: larger">正确答案:
                                <span name="Judge${status.index}" style="color: green;"></span>
                            </div>
                        </fieldset>
                    </form>
                </c:forEach>
                <h3>三、多选题题（每题${exam.multiplescore}分）</h3>
                <c:forEach items="${multiplelist}" var="mul" varStatus="status">
                    <form class="layui-form">
                        <fieldset>
                            <div>
                                <h3 style="margin-top: 10px;margin-bottom: 10px"><%=++mulindex%>、${mul.topic}</h3>
                            </div>
                            <input type="checkbox" name="Multiple${status.index}" value="A"><span
                                style="margin-left: 20px;margin-right: 10px">A:</span>${mul.a}<br/>
                            <input type="checkbox" name="Multiple${status.index}" value="B"><span
                                style="margin-left: 20px;margin-right: 10px">B:</span>${mul.b}<br/>
                            <input type="checkbox" name="Multiple${status.index}" VALUE="C"><span
                                style="margin-left: 20px;margin-right: 10px">C:</span>${mul.c}<br/>
                            <input type="checkbox" name="Multiple${status.index}" value="D"><span
                                style="margin-left: 20px;margin-right: 10px">D:</span>${mul.d}<br/>
                            <br/>
                            <div style="margin-bottom: 20px;font-size: larger">正确答案:
                                <span name="Multiple${status.index}" style="color: green;"></span>
                            </div>
                        </fieldset>
                    </form>
                </c:forEach>
                <br/>
                <br/>

                <div style="text-align:center">
                    <button class="layui-btn  layui-btn-sm" onclick="onclick1()"><span style=""> <i
                            class="layui-icon layui-icon-form"></i>打印</span></button>
                </div>
                <br/>
                <br/>
                <br/>
                <br/>
            </div>
        </div>

        <br/>
        <br/>
    </div>
</div>
<!-- /#page-wrapper -->

<!-- /#wrapper -->

<!-- jQuery -->

<script src="/static/tool/jquery/jquery.js"></script>

<script src="/static/tool/bootstrap/bootstrap.js"></script>

<script src="/static/tool/metisMenu/metisMenu.js"></script>

<script src="/static/tool/dist/sb-admin-2.js"></script>
<script type="text/javascript">
    function onclick1() {
        window.print();
        return;

    }
</script>
<script>

    layui.use(['form', 'laydate', 'layer', 'upload'], function () {
        var form = layui.form,
            layer = layui.layer,
            $ = layui.jquery;

        layui.use('form', function () {
            var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
            <c:forEach items="${selan}" var="sel" varStatus="status">
            <c:choose>
            <%-- 这是 html 注释 --%>
            <c:when test="${ sel == 'A' }">
            $("[name='Select" + ${status.index} +"']").get(0).checked = true;
            </c:when>
            <c:when test="${  sel == 'B' }">
            $("[name='Select" + ${status.index} +"']").get(1).checked = true;
            </c:when>
            <c:when test="${  sel == 'C' }">
            $("[name='Select" + ${status.index} +"']").get(2).checked = true;
            </c:when>
            <c:when test="${ sel == 'D'  }">
            $("[name='Select" + ${status.index} +"']").get(3).checked = true;
            </c:when>

            </c:choose>

            $("[name='Select" + ${status.index} +"']").text("${choicelist.get(status.index).answer}")
            </c:forEach>


            <c:forEach items="${judan}" var="jud" varStatus="status">
            <c:choose>
            <%-- 这是 html 注释 --%>
            <c:when test="${ jud == '正确' }">
            $("[name='Judge" + ${status.index} +"']").get(0).checked = true;
            </c:when>
            <c:when test="${  jud == '错误' }">
            $("[name='Judge" + ${status.index} +"']").get(1).checked = true;
            </c:when>
            </c:choose>
            $("[name='Judge" + ${status.index} +"']").text("${judgelist.get(status.index).answer}")
            </c:forEach>



            <c:forEach items="${Mulan}" var="mul" varStatus="status">
            <c:choose>

            <c:when test="${ mul.contains('A') }">
            $("[name='Multiple" + ${status.index} +"']").get(0).checked = true;
            </c:when>

            <c:when test="${ mul.contains('B') }">
            $("[name='Multiple" + ${status.index} +"']").get(1).checked = true;
            </c:when>

            <c:when test="${ mul.contains('C') }">
            $("[name='Multiple" + ${status.index} +"']").get(2).checked = true;
            </c:when>

            <c:when test="${ mul.contains('D') }">
            $("[name='Multiple" + ${status.index} +"']").get(3).checked = true;
            </c:when>

            </c:choose>
            $("[name='Multiple" + ${status.index} +"']").text("${multiplelist.get(status.index).answer}")
            </c:forEach>

            //但是，如果你的HTML是动态生成的，自动渲染就会失效
            //因此你需要在相应的地方，执行下述方法来进行渲染
            form.render();

        });
    });
</script>

</body>
</html>
