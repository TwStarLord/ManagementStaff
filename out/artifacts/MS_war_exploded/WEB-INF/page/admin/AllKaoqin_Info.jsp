<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <title>员工考勤信息</title>
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
                        <button class="layui-btn" lay-event="batchdownload" id="addKaoqin"><i class="layui-icon" >&#xe600;</i>添加</button>
                        <button class="layui-btn layui-btn-danger" lay-event="batchdelete" id="batchdelete"><i class="layui-icon">&#xe640;</i>批量删除</button>
                        <shiro:hasPermission name="kaoqin:excelexport">
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
            ,url: '${pageContext.request.contextPath}/Kaoqin/FindAllKaoqinInfo'
            ,toolbar: true
            ,title: '员工考勤信息表'
            ,totalRow: true
            ,cols: [[
                {checkbox: true, LAY_CHECKED: false,fixed: true}
                ,{field:'id', title: '序号', width:80, sort: true,}
                ,{field:'jobid', title: '工号', width:80, sort: true,}
                ,{field:'name', title: '姓名', width:80}
                ,{field:'departid', title: '部门编号', width:160, sort: true,templet:function (d) {
                        return d.departid;
                    }}
                ,{field:'departname', title: '部门名称', width:160, templet:function (d) {
                        return d.department.departname;
                    }}
                // ,{field:'departname', title: '部门名称', width:160, sort: true}
                ,{field:'recordtime', title: '打卡时间', width:160}
                ,{field:'checkstatus', title: '打卡状态', width:160,align: "center",templet: function(d){
                        return d.checkstatus == 0 ? '<button class="layui-btn layui-btn-primary layui-btn-sm">正常</button>':'<div class="layui-btn-container"><button class="layui-btn layui-btn-danger layui-btn-sm">迟到</button><button class="layui-btn layui-btn-sm layui-btn-warm" lay-event="changestatus" ">补卡</button></div>';
                    }}
                ,{field:'month', title: '月份', width:80}
                ,{field:'zhengchangshangban',title: '正常上班天数', width:160}
                ,{field:'jiabantime', title: '加班时间', width:160}
                ,{field:'qingjiaday', title: '请假天数', width:160}
                ,{field:'chuchaiday',title: '出差天数', width:160}
                ,{field:'right', width:130, title: '操作',toolbar:'#btndemo',fixed: 'right'}
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

        //==============================补卡操作 start===============================================

        //==============================补卡操作 end===============================================


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
                            if (result.data === "success"){
                                parent.layer.msg('删除成功', { icon: 1, shade: 0.4, time: 1000 });
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
        $("#addKaoqin").click(function ()
        {
            layer.open({
                type: 2,
                title: '添加考勤信息',
                shade: false,
                maxmin: true,
                area: ['90%', '90%'],
                content: '${pageContext.request.contextPath}/Kaoqin/ToInsertKaoqinInfo'
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
                var batchjobid = '';
                for (var i = 0; i < data.length; i++)
                {
                    batchjobid += data[i].jobid + ",";
                }
                $.ajax({
                    url: '${pageContext.request.contextPath}/Kaoqin/exportKaoqinExcel',
                    type: "post",
                    data: { 'batchjobid': batchjobid },
                    success: function (res){
                        console.log(res.data);
                        // 1. 数组头部新增表头
                        res.data.unshift({id:'序号',jobid:'工号',name: '姓名',departid: '部门编号',departname: '部门名称',
                            recordtime: '打卡时间',checkstatus: '打卡状态',month: '月份',zhengchangshangban: '正常上班天数',jiabantime: '加班时间',
                            qingjiaday: '请假天数',chuchaiday: '出差天数'});
                        // 2. 如果需要调整顺序，请执行梳理函数
//departname jiesuanyuefen jibengongzi jiangli chengfa jiabangongzi kuanggonggongzi qingjiagongzi chuchaigongzi shijijiesuan
                        var data = excel.filterExportData(res.data, [
                            'id',
                            'jobid',
                            'name',
                            'departid',
                            'departname',
                            'recordtime',
                            'checkstatus',
                            'month',
                            'zhengchangshangban',
                            'jiabantime',
                            'qingjiaday',
                            'chuchaiday',
                        ]);
                        // 3. 执行导出函数，系统会弹出弹框
                        excel.exportExcel({
                            sheet1: data
                        }, '考勤信息.xlsx', 'xlsx');

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
            var layevent = obj.event;
            console.log(layevent);
            if(layevent == 'changestatus'){
                layer.open({
                    type: 2,
                    title: '考勤补卡申请',
                    shade: false,
                    maxmin: true,
                    area: ['90%', '90%'],
                    content: '${pageContext.request.contextPath}/Kaoqin/ToChangeKaoqinStatus?id='+data.id
                });
                return false;
            }else if(layevent == 'delete'){
                layer.confirm('确认要删除该考勤信息吗?',function(index){
                    window.location='${pageContext.request.contextPath}/DeleteKaoqinInfo?id='+data.id;
                    return false;
                })
            }else if(layevent == 'edit'){
                layer.open({
                    type: 2,
                    title: '考勤信息编辑',
                    shade: false,
                    maxmin: true,
                    area: ['90%', '90%'],
                    content: '${pageContext.request.contextPath}/Kaoqin/ToUpdateKaoqinInfo?id='+data.id
                });
                return false;
            }
        })

    });
</script>
<script type="text/html" id="btndemo">
    <a class='layui-btn  layui-btn-xs layui-btn' lay-event='edit' data-type='download'>编辑</a>
    <a class='layui-btn  layui-btn-xs layui-btn-danger' lay-event='delete'>删除</a>
</script>
</body>
</html>