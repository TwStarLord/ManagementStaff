<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <title>表单组合</title>
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
        <div class="layui-card-header">添加考勤信息</div>
        <div class="layui-card-body" style="padding: 15px;">
            <form class="layui-form" id="kaoqinForm" lay-filter="component-form-group">
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>工号</label>
                    <div class="layui-input-block">
                        <select name="jobid" id="jobidSelect" lay-verify="required" lay-filter="jobidChange">
                            <option>====请选择====</option>
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>姓名</label>
                    <div class="layui-input-block">
                        <input type="text" name="name"  lay-verify="required" value="" id="name"
                               placeholder="姓名" class="layui-input">
                    </div>
                </div>


                <div class="layui-form-item">
                    <label class="layui-form-label">部门编号</label>
                    <div class="layui-input-block">
                        <select name="departid" lay-filter="" id="depaetmentinfo">
                        </select>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>月份</label>
                    <div class="layui-input-block">
                        <select name="month" id="monthSelect" lay-verify="required">
                            <option>====请选择====</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                            <option value="10">10</option>
                            <option value="11">11</option>
                            <option value="12">12</option>
                        </select>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>正常上班天数</label>
                    <div class="layui-input-block">
                        <input type="text" name="zhengchangshangban" lay-verify="required" id="zhengchangshangban"
                               autocomplete="off" placeholder="开始时间" class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>加班时间</label>
                    <div class="layui-input-block">
                        <input type="text" name="jiabantime" lay-verify="required" id="jiabantime"
                               autocomplete="off" placeholder="结束时间" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>请假天数</label>
                    <div class="layui-input-block">
                        <input type="text" name="qingjiaday" lay-verify="required" id="qingjiaday"
                               autocomplete="off" placeholder="目的地" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>出差天数</label>
                    <div class="layui-input-block">
                        <input type="text" name="chuchaiday" lay-verify="required" id="chuchaiday"
                               autocomplete="off" placeholder="状态" class="layui-input">
                    </div>
                </div>

                <div class="layui-btn-container" style='text-align:center;'>
                    <button type="button" class="layui-btn layui-btn-normal layui-btn-radius" lay-submit="" lay-filter="kaoqin-submit">提交</button>
                    <input type="submit" class="layui-btn layui-btn-warm layui-btn-radius">重置</button>
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
            ,form = layui.form;
        //---------------------------------对工号或者姓名的失去焦点绑定事件---------------------


        //-------------------------------------失去焦点事件end---------------------------------


        //---------------------部门联动，省市联动----------------------------------------------


        //---------------------部门联动，省市联动end----------------------------------------------
        form.on('submit(kaoqin-submit)',function(){
            $.post("${pageContext.request.contextPath}/Kaoqin/InsertKaoqinInfo",$("#kaoqinForm").serialize(),function(data){
                if(data=="success"){
                    layer.msg("提交成功！",{icon:1,anim:4,time:2000},function(){
                        //关闭窗口
                        parent.layer.closeAll();
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                        parent.layui.table.reload('allKaoqinInfo');
                    });
                }else{
                    layer.msg("提交！请重试！",{icon:5,anim:6,time:2000});
                }
            });
        });

        /*$(function () {
            $.get("${pageContext.request.contextPath}/Department/FindAllDepartmentInfo",function (data,status) {
                var departstr = "";
                $.each(data, function(index,item){
                    departstr += "<option value=' "+ item.departid +" ' >" + item.departname + "</option>";

                })
                $("#depaetmentinfo").append(departstr);
                //这里需要加上重新渲染，否则数据加载不到select中  坑的yp
                layui.form.render("select");
            })
        });*/

        $(function () {
            $.get("${pageContext.request.contextPath}/StaffInfo/FindAllStaffJobId",function (data,status) {
                var jobidstr = "";
                $.each(data, function(index,item){
                    jobidstr += "<option value=' "+ item +" ' >" + item + "</option>";

                })
                $("#jobidSelect").append(jobidstr);
                //这里需要加上重新渲染，否则数据加载不到select中  坑的yp
                layui.form.render("select");
            })
        });


        form.on('select(jobidChange)',function(data){
            $.ajax({
                type:"post",
                url:"${pageContext.request.contextPath}/StaffInfo/FindStaffInfoByJobId",
                data:{jobid:data.value},
                success:function(data){
                    $("#name").val(data.name);
                    var departinfo = '<option value="' + data.departid + '">' + data.departid + '</option>'
                    $("#depaetmentinfo").html(departinfo);
                    form.render();
                },
                error:function (error) {
                    layer.msg("请求失败,请重新尝试!",{icon : 5,anim : 6,time : 1500});
                }
            });
        });
    });
</script>
</body>
</html>