<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>菜单信息管理</title>
    <!-- 引入 layui.css -->
    <link rel="stylesheet" href="/static/tool/layui-v2.6.7/layui/css/layui.css" media="all">

    <!-- 引入 layui.js -->
    <script src="/static/tool/layui-v2.6.7/layui/layui.js"></script>

    <script src="/static/tool/jquery/jquery.js"></script>
    <style>
        .layui-table-cell{
            height: auto;
        }
        .layui-table-cell .layui-form-checkbox[lay-skin="primary"]{
            top: 50%;
        }
    </style>
</head>
<body >
<br/>

<table class="layui-hide" id="tbStuInfo" lay-filter="test"></table>
<%--头部工具栏--%>
<script type="text/html" id="toolbarDemo">
    <div>


        <div style="float: left">
            <button class="layui-btn  layui-btn-sm" lay-event="add" id="btnAdd">
                <i class="layui-icon layui-icon-form"></i>添加
            </button>
            <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="del" id="btnDel">
                <i class="layui-icon layui-icon-delete"></i>删除选中项
            </button>

        </div>
        <form style="margin:10px;display:inline;"  action="<%=request.getContextPath()%>/Uploadchoice" method="post" enctype="multipart/form-data">
            <input  class="" style="display: inline" id="file" type="file" name="file"/>
            <input style="display: inline ; width: 5%" type="submit" name="submit" id="submit" value="上传" />
        </form>
    </div>
</script>
<%--行工具栏--%>
<script type="text/html" id="barDemo">
    <a class="layui-btn layui-btn-sm" lay-event="edit">
        <i class="layui-icon layui-icon-edit"></i>编辑
    </a>
    <a class="layui-btn layui-btn-danger layui-btn-sm" lay-event="del">
        <i class="layui-icon layui-icon-delete"></i>删除
    </a>
</script>
<script>
    layui.use(['table'], function () {//使用layui的数据表格组件table
        var table = layui.table,
            $ = layui.jquery;

        //设置数据表格各项参数
        table.render({
            elem: '#tbStuInfo'//定义表格容器<table>的id值
            , id: 'xjl'//设置基础参数id，后续多处会使用到此值
            , toolbar: '#toolbarDemo' //开启头部工具栏，其值可为true、default、自定义按钮组，需要设置对应按钮组的id
            , defaultToolbar: ['filter', 'exports', 'print', { //自定义头部工具栏右侧图标。如无需自定义，去除该参数即可
                title: '提示'
                , layEvent: 'LAYTABLE_TIPS'//按钮事件名称
                , icon: 'layui-icon-tips'
            }]
            , title: '题库'
            , url: '<%=request.getContextPath()%>/allmultipleChoice', //数据接口，为json格式，使用jsp获取获取项目名称
            // url:'../ch15/stuInfo.json',
            height: 'full',//设置表格高度，离浏览器窗口上下各100px
            cols: [[ //设置表头
                {type: "checkbox", width: 50},
                {field: 'id', title: '试题编号', sort: true ,  templet: '#id'},
                {field: 'topic', title: '题目'},
                {field: 'type', title: '类型', sort: true},
                {field: 'difficulty', title: '难度', sort: true},
                {title: '操作', minWidth: 150, toolbar: '#barDemo', align: "center"}//绑定按钮组，需指定其id
            ]],
            limits: [5, 10, 15, 20, 25, 50, 100],//每页条数的选择项
            limit: 10,//每页显示的条数（默认：10）
            page: true,//开启分页
            parseData: function (res) { //res为原始返回的数据，需要将其拆分成分页数据
                var result;
                if (this.page.curr) {//如果不是第1页
                    result = res.data.slice(this.limit * (this.page.curr - 1), this.limit * this.page.curr);
                } else {
                    result = res.data.slice(0, this.limit);//获取原始数据1-10条数据
                }
                return {
                    "code": res.code, //解析接口状态
                    "msg": res.msg, //解析提示文本
                    "count": res.count, //解析数据长度
                    "data": result //解析数据列表
                }
            }, done: function (res, curr, count) {//表格渲染完成后的回调函数
                bindTableToolbarFunction();//绑定表格自定义按钮的事件，防止表格reload后，自定义按钮事件失效
            }
        });

        //定义表格头部自定义按钮”添加“、”删除选中“的事件
        function bindTableToolbarFunction() {
            $('#btnAdd').click(function () {//定义表格头部单击添加事件
                // window.open("<%=request.getContextPath()%>/stuinfoM/addStu.jsp");//直接跳转
                var index = layer.open({
                    title: '',
                    type: 2,//type为1 ，content显示的是纯文本内容，type为2，content为跳转页面
                    shade: 0.2,
                    // maxmin:true,
                    shadeClose: true,
                    area: ['50%', '90%'],
                    offset: ['5%', '25%'],
                    content: '<%=request.getContextPath()%>/addmultipleChoice',
                    end: function () {
                        location.reload();//弹出层结束后，刷新页面
                    }
                });
            });
            $('#btnDel').click(function () {//点击删除按钮事件
                var checkStatus = table.checkStatus('xjl');//stuinfo 即为基础参数 id 对应的值
                var data = checkStatus.data;//获取被中行的数据
                var arrId = [];//定义数组，存放选中的ID
                var arrTopic = [];////定义数组，存放选中的图片文件名
                if (data.length == 0) {
                    layer.msg("未选则数据，请重新选择！")
                } else {
                    // for (i = 0; i < data.length; i++) {
                    //     arr[i]=data[i].sno;
                    // }
                    data.forEach(function (data) {//遍历选中的行
                        arrId.push(data. id);//将选中的学号存放在数组中
                        arrTopic.push(data.topic);//将选中的图片存0.放在数组中
                    });
                    //在弹出层中不能直接输出数组或对象，可通过JSON.stringify(arr)转换成json字符串显示
                    layer.confirm('真的删除序号为' + JSON.stringify(arrId) + '的记录吗？', function (index) {
                        // window.location='/layuimini/DelStu_16_Servlet?xh='+data.sno//直接跳转
                        $.ajax({//无刷新提交
                            url: '<%=request.getContextPath()%>/deletemultipleChoice',
                            dataType: 'text',//返回的数据类型
                            data: {ids:arrId, topics: arrTopic},//传递给DelStuServlet的参数为xh，DelStuServlet中使用request.getParameterValues("xh");获取数组arr
                            traditional: true,//传递数组参数到Servlet时，必须设置为true，默认为false
                            success: function (data) {//提交成功后，对返回数据的处理，返回数据存放在result中
                                var jsonObject = eval("(" + data + ")");
                                if (jsonObject.flag == true) {
                                    table.reload('xjl', {});//刷新数据表格，stuinfo为table的基础参数id的值
                                    layer.msg('序号为' + JSON.stringify(arrId) + '的记录删除成功！')
                                } else {
                                    table.reload('xjl', {});//刷新数据表格，stuinfo为table的基础参数id的值
                                    layer.msg("记录id为"+jsonObject.data+"已存在于试卷，删除失败")

                                }
                            }
                        });
                        layer.close(index);//点击确定后关闭弹出层
                    });
                }
            });
        }

        //监听行工具事件，主要是编辑、删除指定行
        table.on('tool(test)', function (obj) {//设置表格标签的属性lay-filter="test"
            var data = obj.data;//获取所点击行的数据
            var id = [data.id];//获取所点击行的学号数据
            var topics= [data.topic];//获取所点击行的头像文件名数据
            if (obj.event === 'del') {
                layer.confirm('真的删除内容为【' + JSON.stringify(data) + '】的记录吗？', function (index) {
                    $.ajax({
                        url: '<%=request.getContextPath()%>/deletemultipleChoice',
                        dataType: 'text',
                        data: {ids: id, topics: topics},
                        traditional: true,//传递数组参数到Servlet时，必须设置为true，默认为false
                        success: function (data) {//提交成功后，对返回数据的处理，返回数据存放在result中
                            var jsonObject = eval("(" + data + ")");
                            if (jsonObject.flag == true) {
                                table.reload('xjl', {});//刷新数据表格，stuinfo为table的基础参数id的值
                                layer.msg('序号为' + JSON.stringify(arrId) + '的记录删除成功！')
                            } else {
                                table.reload('xjl', {});//刷新数据表格，stuinfo为table的基础参数id的值
                                layer.msg("记录id为"+jsonObject.data+"已存在于试卷，删除失败")

                            }
                        }
                    });
                    layer.close(index);
                });
            } else if (obj.event === 'edit') {
                // window.open('modifyStu.jsp?xh=' + data.sno);
                var index = layer.open({
                    title: '',
                    type: 2,//type为1 ，content显示的是纯文本内容，type为2，content为跳转页面
                    shade: 0.2,
                    // maxmin:true,
                    shadeClose: true,
                    area: ['50%', '90%'],
                    offset: ['5%', '25%'],
                    content: '<%=request.getContextPath()%>/updatemultipleChoicejsp?id=' + data.id,
                    end: function () {
                        location.reload();//弹出层结束后，刷新主页面
                    }
                });
            }
        });



    });


</script>
</body>
</html>
