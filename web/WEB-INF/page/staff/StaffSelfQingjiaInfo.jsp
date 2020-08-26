<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>个人请假信息</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/style/admin.css" media="all">
</head>
<body>

<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">
                    <div class="layui-btn-container">
                        <button class="layui-btn" lay-event="batchdownload" id="addQingjia"><i class="layui-icon" >&#xe600;</i>添加</button>
                        <button class="layui-btn layui-btn-danger" lay-event="batchdelete" id="batchdelete"><i class="layui-icon">&#xe640;</i>批量删除</button>
                        <shiro:hasPermission name="qingjia:excelexport">
                            <button class="layui-btn layui-btn-primary" lay-event="exportExcel" id="exportExcel"><i class="layui-icon">&#xe640;</i>导出Excel</button>
                        </shiro:hasPermission>
                    </div>
                </div>
                <div class="layui-card-body">

                    <div class="test-table-reload-btn" style="margin-bottom: 10px;">
                        搜索：
                        <!-- 由输入框获取关键字 -->
                        <div class="layui-inline">
                            <input class="layui-input" name="name" id="nameToReload" lay-verify="required" autocomplete="off" placeholder="姓名">
                        </div>
                        <div class="layui-inline">
                            <select class="layui-input" name="departid" lay-filter="" id="depaetmentinfo"></select>
                        </div>
                        <div class="layui-inline">
                            <input class="layui-input" name="starttime" id="starttimeToReload" lay-verify="required" autocomplete="off" placeholder="开始时间" >
                        </div>
                        <div class="layui-inline">
                            <input class="layui-input" name="endtime" id="endtimeToReload" lay-verify="required" autocomplete="off" placeholder="结束时间" >
                        </div>
                        <button class="layui-btn" data-type="reload">搜索</button>
                    </div>

                    <table class="layui-hide" id="tableToReload" lay-filter="demo"></table>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${pageContext.request.contextPath}/layui/layuiadmin/layui/layui.js"></script>
<script>
    layui.config({
        base: '${pageContext.request.contextPath}/layui/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'table','jquery','excel'], function(){
        var table = layui.table,
            $ = layui.jquery,
            excel = layui.excel;

        $(function () {
            $.get("${pageContext.request.contextPath}/Department/FindAllDepartmentInfo",function (data,status) {
                var departstr = "<option value=''>==部门==</option>";
                $.each(data, function(index,item){
                    departstr += "<option value=' "+ item.departid +" '>" + item.departname + "</option>";

                })
                $("#depaetmentinfo").append(departstr);
                //这里需要加上重新渲染，否则数据加载不到select中  坑的yp
                layui.form.render("select");
            })
        });

        //方法级渲染
        table.render({
            elem: '#tableToReload'
            ,url: '${pageContext.request.contextPath}/Qingjia/FindSelfQingjiaInfo'
            ,toolbar: true
            ,title: '员工请假信息表'
            ,totalRow: true
            ,cols: [[
                {checkbox: true, LAY_CHECKED: false,fixed: true}
                ,{field:'id', title: '序号', width:80, sort: true,}
                ,{field:'jobid', title: '工号', width:80, sort: true,}
                ,{field:'name', title: '姓名', width:80}
                ,{field:'departid', title: '部门编号', width:160, sort: true/*,templet: function (d) {
              // info = JSON.parse(JSON.stringify(d));
              return d.departid;
          }*/}
                ,{field:'departname', title: '部门名称', width:160,templet:function (d) {
                    return d.department.departname;
                    }}
                ,{field:'starttime', title: '开始时间', width:80}
                ,{field:'endtime', title: '结束时间', width:160}
                ,{field:'status',title: '状态', width:160}
                ,{field:'cause', title: '出差事由', width:160,}
                ,{field:'descripetion', title: '描述', width:160}
                ,{field:'subdate', title: '提交日期', width:160}
                ,{field:'shenpi',title: '审批人', width:160}
                ,{field:'right', width:130, title: '操作',align:'center',fixed: 'right',templet:function (d) {
                        if(d.status === "同意"){
                            return '<button class=\'layui-btn  layui-btn-xs layui-btn\' lay-event=\'endholiday\'>销假</button>';
                        }else if(d.status === "不同意"){
                            return '<button class=\'layui-btn-danger  layui-btn-xs layui-btn\'>该请求未被同意</button>';
                        }else if(d.status === "待审核"){
                            return '<button class=\'layui-btn-warm  layui-btn-xs layui-btn\'>待审核</button>';
                        }
                    }}
            ]]
            ,page: true
            ,limits:[5,10,15]
            ,height: 'full-100'
            ,response: {//自定义返回码状态
                statusCode: 0 //重新规定成功的状态码为 200，table 组件默认为 0
            }
            ,parseData: function(res){ //将原始数据解析成 table 组件所规定的数据
                return {
                    "data": res.data,//解析数据列表
                    "count": res.count,//解析数据长度
                    "msg": res.msg,//解析提示文本
                    "code": res.code //解析接口状态
                };
            }
        });
        //--------------------------批量删除----------------------------------
        $("#batchdelete").click(function ()
        {
            var checkStatus = table.checkStatus('tableToReload');
            var count = checkStatus.data.length;//选中的行数
            if (count > 0)
            {
                parent.layer.confirm('确定删除？', { icon: 3 }, function (index)
                {
                    var data = checkStatus.data; //获取选中行的数据
                    var batchjobid = '';
                    for (var i = 0; i < data.length; i++)
                    {
                        batchjobid += data[i].jobid + ",";
                    }
                    $.ajax({
                        url: '${pageContext.request.contextPath}/BatchDeleteQingjia',
                        type: "post",
                        data: { 'batchjobid': batchjobid },
                        success: function (result){
                            if (result=="success"){
                                parent.layer.msg('删除成功', { icon: 1, shade: 0.4, time: 1000 })
                                $("#search").click();//重载TABLE
                            }else{
                                parent.layer.msg("删除失败", { icon: 5, shade: [0.4], time: 1000 });
                            }
                            parent.layer.close(index);
                        }
                    })
                });
            }
            else
                parent.layer.msg("请至少选择一条数据", { icon: 5, shade: 0.4, time: 1000 });
        });
        //-----------------------------批量删除end--------------------------------------


        //--------------------------批量下载----------------------------------
        $("#addQingjia").click(function ()
        {
            layer.open({
                type: 2,
                title: '请假信息添加',
                shade: false,
                maxmin: true,
                area: ['90%', '90%'],
                content: '${pageContext.request.contextPath}/Qingjia/ToInsertQingjiaInfo'
            });

        });
        //-----------------------------批量下载end--------------------------------------


        var $ = layui.$, active = {
            reload: function(){
                var nameReload = $('#nameToReload');
                var departnameReload = $('#depaetmentinfo');
                var starttimeReload = $('#starttimeToReload');
                var endtimeReload = $('#endtimeToReload');
                var count = 4;
                //由于后台sql自会进行模板判定，可以不用数组收纳
                /* alert(($('#nameToReload').val())=='');   经过测试，没有输入查询关键字时，该判断为true，所以还是采用反向计算 */
                if(nameReload.val() == null||nameReload.val() == ''){
                    count--;
                }
                if(departnameReload.val() ==null||departnameReload.val()==''){
                    count--;
                }
                if(starttimeReload.val()==null||starttimeReload.val()==''){
                    count--;
                }
                if(endtimeReload.val()==null||endtimeReload.val()==''){
                    count--;
                }
                /*         alert("查询关键字数为："+count); */
                //需要加上查询条件检查，为空则不进行查询，并给出提示
                if(count<=0){
                    layer.msg("请输入查询关键字!",{icon: 5})
                    return false;
                }else{
                    //执行重载
                    table.reload('tableToReload', {
                        //设置为post请求，否则会有中文乱码 ？此处是不是已经做过了编码处理，不设置为post时，会导致控制器在接收到中文信息时以乱码显示（spring）自带的编码处理器已经加上了
                        method:'POST',
                        page: {
                            curr: 1 //重新从第 1 页开始
                        }
                        ,where: {
                            name:nameReload.val(),
                            departid:departnameReload.val(),
                            starttime:starttimeReload.val(),
                            endtime:endtimeReload.val()
                        }
                    });
                }

            }
        };

        $('.test-table-reload-btn .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        //--------------------------excel导出----------------------------------

        $("#exportExcel").click(function ()
        {
            var checkStatus = table.checkStatus('tableToReload');
            var count = checkStatus.data.length;//选中的行数
            if (count > 0)
            {
                var data = checkStatus.data; //获取选中行的数据
                var batchid = '';
                for (var i = 0; i < data.length; i++)
                {
                    batchid += data[i].id + ",";
                }
                $.ajax({
                    url: '${pageContext.request.contextPath}/Qingjia/exportQingjiaExcel',
                    type: "post",
                    data: { 'batchid': batchid },
                    success: function (res){
                        console.log(res.data);
                        // 1. 数组头部新增表头
                        res.data.unshift({id:'序号',jobid:'工号',name: '姓名',departid: '部门编号',departname: '部门名称',
                            starttime: '开始时间',endtime: '结束时间',status: '状态',cause: '事由',descripetion: '描述',
                            subdate: '提交日期',shenpi: '审批人'});
                        // 2. 如果需要调整顺序，请执行梳理函数
//departname jiesuanyuefen jibengongzi jiangli chengfa jiabangongzi kuanggonggongzi qingjiagongzi chuchaigongzi shijijiesuan
                        var data = excel.filterExportData(res.data, [
                            'id',
                            'jobid',
                            'name',
                            'departid',
                            'departname',
                            'starttime',
                            'endtime',
                            'status',
                            'cause',
                            'descripetion',
                            'subdate',
                            'shenpi',
                        ]);
                        // 3. 执行导出函数，系统会弹出弹框
                        excel.exportExcel({
                            sheet1: data
                        }, '请假信息.xlsx', 'xlsx');

                        /*                                 parent.layer.close(index);
                         */                            }
                })

            }
            else
                parent.layer.msg("请至少选择一条数据", { icon: 5, shade: 0.4, time: 1000 });
        });
        //-----------------------------导出Excel end--------------------------------------

        table.on('tool(demo)',function(obj){
            var data = obj.data;
            if(obj.event == 'endholiday'){
                $.ajax({
                    url:"${pageContext.request.contextPath}/Qingjia/PostEndHolidayApply",
                    type:"post",
                    data:{qingjia_id:data.id},
                    success:function (data) {
                        if(data === "success"){
                            layer.msg("已提交销假申请!");
                        }else if (data === "fail"){
                            layer.msg("提交销假申请失败!");
                        }else{
                            layer.msg("未知错误!");
                        }
                    }
                })

            }
        })

    });
</script>
</body>
</html>