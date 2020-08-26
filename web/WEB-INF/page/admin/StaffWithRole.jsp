<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>员工出差信息</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/layui/css/layui.css" media="all">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/style/admin.css" media="all">
</head>
<body>
<input type="hidden" name="id" id="roleid" value="">

<div class="layui-fluid">
    <div class="layui-row layui-col-space15">
        <div class="layui-col-md12">
            <div class="layui-card">
                <div class="layui-card-header">
                    <div class="layui-btn-container">
                        <button class="layui-btn" id="addRoleStaff"><i class="layui-icon" >&#xe600;</i>添加已有员工</button>
                        <button class="layui-btn layui-btn-danger"  id="batchdelete"><i class="layui-icon">&#xe640;</i>批量删除</button>
                    </div>
                </div>
                <div class="layui-card-body">

                    <div class="test-table-reload-btn" style="margin-bottom: 10px;">
                        搜索：
                        <!-- 由输入框获取关键字 -->
                        <div class="layui-inline">
                            <select class="layui-input" name="departid" id="depaetmentinfo"></select>
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
    }).use(['index', 'table','jquery','excel','laydate'], function(){
        var table = layui.table,
            $ = layui.jquery,
            excel = layui.excel,
            laydate = layui.laydate;
            var id = $("#roleid").val(); //这里是角色信息界面传过来的角色id值，根据角色查询对应权限。
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
            ,url:'${pageContext.request.contextPath}/Role/FindStaffOfRole?id='+id
            ,toolbar: true
            ,title: '员工角色信息表'
            ,totalRow: true
            ,cols: [[
                {checkbox: true, LAY_CHECKED: false,fixed: true}
                ,{field:'jobid', width:80, title: '工号', sort: true}
                ,{field:'name', width:80, title: '姓名'}
                ,{field:'account', width:80, title: '账号'}
                ,{field:'departid', minWidth:80, title: '部门编号',templet: function(d){
                        return d.departid;
                    }}
                ,{field:'role_name', minWidth:80, title: '角色信息'}
                /*,{field:'departid', title: '部门名称', minWidth: 80,templet: function(d){
                        return d.department.departname;
                    }}*/
                ,{field:'status', width:80, title: '状态',align:'center',templet:function (d) {
                        if (d.status == "在职"){
                            return '<div><button class="layui-btn layui-btn-sm layui-btn" >在职</button></div>';
                        }else if(d.status == "请假"){
                            return '<div><button class="layui-btn layui-btn-sm layui-btn-danger" >请假</button></div>';
                        }else if(d.status == "出差"){
                            return '<div><button class="layui-btn layui-btn-sm layui-btn-danger" >出差</button></div>';
                        }else if(d.status == "离职"){
                            return '<div><button class="layui-btn layui-btn-sm layui-btn-danger">离职</button></div>';
                        }else if(d.status == "待激活"){
                            return '<div><button class="layui-btn layui-btn-sm layui-btn-warm" >待激活</button></div>';
                        }

                    }}
                ,{field:'right', width:250, title: '操作',align:'center',fixed: 'right',templet:function (d) {
                    if(d.role_name === "超级管理员"){
                        return '<button class=\'layui-btn-danger  layui-btn-xs layui-btn\' >当前角色不可操作</button>';
                    }else if(d.role_name === "人事部主管"){
                        return '<button class=\'layui-btn  layui-btn-xs layui-btn\' lay-event=\'edit\'>编辑</button>\n' +
                            '    <button class=\'layui-btn-danger  layui-btn-xs layui-btn\' lay-event=\'delete\'>删除</button>\n' +
                            '    <button class=\'layui-btn-danger  layui-btn-xs layui-btn\' lay-event=\'downstaff\'>降为普通员工</button>';
                    }else if(d.role_name === "财务部主管"){
                        return '<button class=\'layui-btn  layui-btn-xs layui-btn\' lay-event=\'edit\'>编辑</button>\n' +
                            '    <button class=\'layui-btn-danger  layui-btn-xs layui-btn\' lay-event=\'delete\'>删除</button>\n' +
                            '    <button class=\'layui-btn-danger  layui-btn-xs layui-btn\' lay-event=\'downstaff\'>降为普通员工</button>';
                    }else if(d.role_name === "技术部主管"){
                        return '<button class=\'layui-btn  layui-btn-xs layui-btn\' lay-event=\'edit\'>编辑</button>\n' +
                            '    <button class=\'layui-btn-danger  layui-btn-xs layui-btn\' lay-event=\'delete\'>删除</button>\n' +
                            '    <button class=\'layui-btn-danger  layui-btn-xs layui-btn\' lay-event=\'downstaff\'>降为普通员工</button>';
                    }else if(d.role_name === "普通员工"){
                        return '<button class=\'layui-btn  layui-btn-xs layui-btn\' lay-event=\'edit\'>编辑</button>\n' +
                            '    <button class=\'layui-btn-danger  layui-btn-xs layui-btn\' lay-event=\'delete\'>删除</button>\n' +
                            '    <button class=\'layui-btn-danger  layui-btn-xs layui-btn\' lay-event=\'upstaff\'>升为部门主管</button>';
                    }
                    }}
            ]]
            ,page: true
            ,height: 'full-100'
            ,response: {
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
                        url: '${pageContext.request.contextPath}/StaffInfo/BatchDeleteStaffInfo',
                        type: "post",
                        data: { 'batchjobid': batchjobid },
                        success: function (result){
                            if (result=="success"){
                                parent.layer.msg('批量删除成功', { icon: 1, shade: 0.4, time: 1000 })
                                $("#search").click();//重载TABLE
                            }else{
                                parent.layer.msg("批量删除失败", { icon: 5, shade: [0.4], time: 1000 });
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



        var $ = layui.$, active = {
            reload: function(){
                var departnameReload = $('#depaetmentinfo');
                var count = 1;
                if(departnameReload.val() ==null||departnameReload.val()==''){
                    count--;
                }
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
                            departid:departnameReload.val()
                        }
                    });
                }

            }
        };

        /*$('#exportExcel').on('click', function(){
          var type = $(this).data('type');
          active[type] && active[type].call(this);
          console.log(this);
        });*/

        $('.test-table-reload-btn .layui-btn').on('click', function(){
            var type = $(this).data('type');
            active[type] ? active[type].call(this) : '';
        });

        //--------------------------添加角色员工start----------------------------------
        $("#addRoleStaff").click(function ()
        {
            layer.open({
                type: 2,
                title: '添加已有员工',
                shade: false,
                maxmin: true,
                area: ['90%', '90%'],
                content: '${pageContext.request.contextPath}/StaffInfo/ToInsertStaffRoleInfo?roleid='+id
            });

        });

        //-----------------------------添加角色员工end--------------------------------------


        table.on('tool(demo)',function(obj){
            var data = obj.data;
            if(obj.event =='delete'){
                layer.confirm('确认要删除该员工信息吗?',function(index){
                    window.location='${pageContext.request.contextPath}/StaffInfo/DeleteStaff?jobid='+data.jobid;

                })
            }else if(obj.event == 'edit'){
                layer.open({
                    type: 2,
                    title: '员工信息编辑',
                    shade: false,
                    maxmin: true,
                    area: ['90%', '90%'],
                    content: '${pageContext.request.contextPath}/StaffInfo/ToUpdateStaff?jobid='+data.jobid
                });
            }else if(obj.event == 'upstaff'){
                $.ajax({
                    url:"${pageContext.request.contextPath}/StaffInfo/UpStaffRole",
                    type:"post",
                    data:{jobid:data.jobid},
                    success:function (result) {
                        if(result === "success"){
                            layer.msg("成功!");
                        }else if(result === "fail"){
                            layer.msg("失败!");
                        }else {
                            layer.msg("未知错误!");
                        }
                    },
                    error:function (error) {
                        layer.msg("请求失败，请重新尝试!");
                    }
                })
            }else if(obj.event == 'downstaff'){
                $.ajax({
                    url:"${pageContext.request.contextPath}/StaffInfo/DownStaffRole",
                    type:"post",
                    data:{jobid:data.jobid},
                    success:function (result) {
                        if(result === "success"){
                            layer.msg("成功!");
                        }else if(result === "fail"){
                            layer.msg("失败!");
                        }else {
                            layer.msg("未知错误!");
                        }
                    },
                    error:function (error) {
                        layer.msg("请求失败，请重新尝试!");
                    }
                })
            }
        })

    });
</script>
<%--<script type="text/html" id="manager">
    <button class='layui-btn  layui-btn-xs layui-btn' lay-event='edit'>编辑</button>
</script>--%>

<script type="text/html" id="managementstaff">
    <button class='layui-btn  layui-btn-xs layui-btn' lay-event='edit'>编辑</button>
    <button class='layui-btn-danger  layui-btn-xs layui-btn' lay-event='delete'>删除</button>
    <button class='layui-btn-danger  layui-btn-xs layui-btn' lay-event='downstaff'>降为普通员工</button>
</script>

<script type="text/html" id="commonstaff">
    <button class='layui-btn  layui-btn-xs layui-btn' lay-event='edit'>编辑</button>
    <button class='layui-btn-danger  layui-btn-xs layui-btn' lay-event='delete'>删除</button>
    <button class='layui-btn-danger  layui-btn-xs layui-btn' lay-event='upstaff'>升为部门主管</button>
</script>
</body>
</html>