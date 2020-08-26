<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>设置密码</title>
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
                <div class="layui-card-header">修改密码</div>
                <div class="layui-card-body" pad15>

                    <input type="hidden" name="jobid" value="${staffinfo.jobid}" id="jobidValue">

                    <div class="layui-form" lay-filter="">
                        <div class="layui-form-item">
                            <label class="layui-form-label">当前密码</label>
                            <div class="layui-input-inline">
                                <input type="password" id="oldpassword" lay-verify="required" lay-verType="tips" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">新密码</label>
                            <div class="layui-input-inline">
                                <input type="password" id="newpassword" lay-verify="pass" lay-verType="tips" autocomplete="off"  class="layui-input">
                            </div>
                            <div class="layui-form-mid layui-word-aux">5到12个字符</div>
                        </div>
                        <div class="layui-form-item">
                            <label class="layui-form-label">确认新密码</label>
                            <div class="layui-input-inline">
                                <input type="password" id="repassword"  lay-verify="repass" lay-verType="tips" autocomplete="off" class="layui-input">
                            </div>
                        </div>
                        <div class="layui-form-item">
                            <div class="layui-input-block">
                                <button class="layui-btn" lay-submit lay-filter="setmypass">确认修改</button>
                            </div>
                        </div>
                    </div>

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
    }).use(['index', 'layer','form','setter','laytpl','view','jquery'],function () {
            var $ = layui.jquery
            ,layer = layui.layer
            ,laytpl = layui.laytpl
            ,setter = layui.setter
            ,view = layui.view
            ,admin = layui.admin
            ,form = layui.form;
        form.verify({
            pass: [
            /^[\S]{5,12}$/
            ,'密码必须5到12位，且不能出现空格'
        ]

            //确认密码
            ,repass: function(value){
            if(value !== $('#newpassword').val()){
                return '两次密码输入不一致';
            }
        }
        });

        //设置密码
        form.on('submit(setmypass)', function(obj){
            // layer.msg(JSON.stringify(obj.field));
            var jobid = $("#jobidValue").val();
            var oldpassword = $("#oldpassword").val();
            var newpassword = $("#newpassword").val();
            $.ajax({
                url:"${pageContext.request.contextPath}/StaffInfo/ChangePassword",
                type:"post",
                data:{jobid:jobid,oldpassword:oldpassword,newpassword:newpassword},
                success:function (data) {
                    if(data == "pwdError"){
                        layer.msg("密码错误!");
                    }else if(data == "success"){
                        layer.msg("修改成功!");
                    }else{
                        layer.msg("修改失败!");
                    }
                },
                error:function (error) {
                    layer.msg("提交失败，请重新尝试!");
                }
            })
            //提交修改
            /*
            admin.req({
              url: ''
              ,data: obj.field
              ,success: function(){

              }
            });
            */
            // return false;
        });
    });
</script>
</body>
</html>