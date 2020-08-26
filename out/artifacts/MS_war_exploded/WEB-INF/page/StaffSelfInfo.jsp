<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import="com.tw.pojo.Staff" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>个人信息</title>
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
                <div class="layui-card-header">个人信息</div>
                <div class="layui-card-body">
                    <table class="layui-hide" id="staffSelfInfo"></table>
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
    }).use(['index', 'table','jquery'], function(){
        var table = layui.table,
        $ = layui.jquery;


                        table.render({
                            elem: '#staffSelfInfo'
                            ,url: '${pageContext.request.contextPath}/StaffInfo/FindSelfStaffInfo'
                            ,title: '员工个人信息表'
                            ,totalRow: true
                            ,cols: [[ //标题栏
                                {type: 'numbers', fixed: 'left'}
                                ,{field: 'jobid', title: '工号', width: 80, sort: true}
                                ,{field: 'name', title: '姓名', width: 120}
                                ,{field: 'image', title: '头像', minWidth: 150}
                                ,{field: 'account', title: '账号', minWidth: 160}
                                ,{field: 'departid', title: '部门编号', width: 80}
                                ,{field: 'departname', title: '部门名称', width: 100,templet:function (d) {
                                        return d.department.departname;
                                    }}
                                ,{field: 'sex', title: '性别', width: 80, sort: true}
                                ,{field: 'birthday', title: '出生日期', width: 80, sort: true}
                                ,{field: 'eduback', title: '学历', width: 80, sort: true}
                                ,{field: 'mobile', title: '联系电话', width: 80, sort: true}
                                ,{field: 'mail', title: '邮箱', width: 80, sort: true}
                                ,{field: 'address', title: '地址', width: 80, sort: true}
                                ,{field: 'status', title: '在职状态', width: 80, sort: true}
                                ,{field: 'timeforjob', title: '入职日期', width: 80, sort: true}
                                ,{field: 'descripetion', title: '简介', width: 80, sort: true}
                            ]]
                            ,page: true
                            ,limits:[5,10,15]
                            ,height: 315
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
    });
</script>
</body>
</html>