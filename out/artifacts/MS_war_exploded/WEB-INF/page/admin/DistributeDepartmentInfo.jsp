<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <title>分配部门</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/layui/layuiadmin/layui/css/layui.css"
          media="all">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/layui/layuiadmin/style/admin.css"
          media="all">
</head>
<body>
<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-card-header">分配部门</div>
        <div class="layui-card-body" style="padding: 15px;">
            <form class="layui-form" id="staffForm" lay-filter="component-form-group">

                <input type="hidden" name="jobid" value="${staffdepartinfo.jobid}">

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>姓名</label>
                    <div class="layui-input-block">
                        <input type="text" name="name"  lay-verify="required"
                               placeholder="姓名" class="layui-input" value="${staffdepartinfo.name}" disabled>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>账号</label>
                    <div class="layui-input-block">
                        <input type="text" name="account" lay-verify="required"
                               placeholder="账号(全称小写)" class="layui-input" value="${staffdepartinfo.account}" disabled>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">部门</label>
                    <div class="layui-input-block">
                        <select name="departid" lay-filter="" id="depaetmentinfo" lay-verify="required">
                            <option value="">====请选择====</option>
                        </select>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>状态</label>
                    <div class="layui-input-block">
                        <input type="text" name="status" lay-verify="required" id="status"
                               autocomplete="off" value="${staffdepartinfo.status}" class="layui-input" disabled>
                    </div>
                </div>

                <div class="layui-btn-container" style='text-align:center;'>
                    <button type="button" class="layui-btn layui-btn-normal layui-btn-radius" lay-submit="" lay-filter="instaff-submit">提交</button>
                    <input type="reset" class="layui-btn layui-btn-warm layui-btn-radius">重置</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/layui/layuiadmin/layui/layui.js"></script>
<script>
    layui.config({
        base: '${pageContext.request.contextPath}/layui/layuiadmin/' //静态资源所在路径
    }).extend({
        index: 'lib/index' //主入口模块
    }).use(['index', 'form', 'laydate'], function(){
        var $ = layui.$
            ,admin = layui.admin
            ,element = layui.element
            ,layer = layui.layer
            ,laydate = layui.laydate
            ,form = layui.form;

        //---------------------------------对工号或者姓名的失去焦点绑定事件---------------------


        //-------------------------------------失去焦点事件end---------------------------------


        //---------------------部门联动，省市联动----------------------------------------------


        //---------------------部门联动，省市联动end----------------------------------------------
        form.on('submit(instaff-submit)',function(){
            $.post("${pageContext.request.contextPath}/StaffInfo/ChangeStaffDepartmentInfo",$("#staffForm").serialize(),function(data){
                if(data=="success"){
                    layer.msg("提交成功！",{icon:1,anim:4,time:2000},function(){
                        //关闭窗口
                        parent.layer.closeAll();
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                        parent.layui.table.reload('allmessage');
                        parent.layui.table.reload('noticemessage');
                    });
                }else{
                    layer.msg("提交！请重试！",{icon:5,anim:6,time:2000});
                }
            });
        });

        $(function () {
            $.get("${pageContext.request.contextPath}/Department/FindAllDepartmentInfo",function (data,status) {
                var departstr = "";
                $.each(data, function(index,item){
                    if(item.departid!=1){
                        departstr += "<option value=' "+ item.departid +" ' >" + item.departname + "</option>";
                    }
                })
                $("#depaetmentinfo").append(departstr);
                //这里需要加上重新渲染，否则数据加载不到select中  坑的yp
                layui.form.render("select");
            })
        })

    });
</script>
</body>
</html>