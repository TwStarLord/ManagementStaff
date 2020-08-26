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
        <div class="layui-card-header">添加员工薪资信息</div>
        <div class="layui-card-body" style="padding: 15px;">
            <form class="layui-form" id="salaryForm" lay-filter="component-form-group">
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
                        <select name="jiesuanyuefen" id="jiesuanyuefenSelect" lay-verify="required">
                            <option>====结算月份====</option>
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
                    <label class="layui-form-label"><span class="x-red">*</span>基本工资</label>
                    <div class="layui-input-block">
                        <input type="text" name="jibengongzi" lay-verify="required" id="jibengongzi"
                               autocomplete="off" placeholder="基本工资" class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>奖金</label>
                    <div class="layui-input-block">
                        <input type="text" name="jiangli" lay-verify="required" id="jiangli"
                               autocomplete="off" placeholder="奖金" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>惩金</label>
                    <div class="layui-input-block">
                        <input type="text" name="chengfa" lay-verify="required"
                               autocomplete="off" placeholder="惩金" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>加班工资</label>
                    <div class="layui-input-block">
                        <input type="text" name="jiabangongzi" lay-verify="required"
                               autocomplete="off" placeholder="加班工资" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>旷工工资</label>
                    <div class="layui-input-block">
                        <input type="text" name="kuanggonggongzi" lay-verify="required"
                               autocomplete="off" placeholder="旷工工资" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>请假工资</label>
                    <div class="layui-input-block">
                        <input type="text" name="qingjiagongzi" lay-verify="required"
                               autocomplete="off" placeholder="请假工资" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>出差工资</label>
                    <div class="layui-input-block">
                        <input type="text" name="chuchaigongzi" lay-verify="required"
                               autocomplete="off" placeholder="出差工资" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>实际工资</label>
                    <div class="layui-input-block">
                        <input type="text" name="shijijiesuan" lay-verify="required"
                               autocomplete="off" placeholder="实际工资" class="layui-input">
                    </div>
                </div>
                <div class="layui-btn-container" style='text-align:center;'>
                    <button type="button" class="layui-btn layui-btn-normal layui-btn-radius" lay-submit="" lay-filter="salary-submit">提交</button>
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
            ,laydate = layui.laydate
            ,form = layui.form;

        laydate.render({
            elem: '#starttime'
        });
        laydate.render({
            elem: '#endtime'
        });
        //---------------------------------对工号或者姓名的失去焦点绑定事件---------------------


        //-------------------------------------失去焦点事件end---------------------------------


        //---------------------部门联动，省市联动----------------------------------------------


        //---------------------部门联动，省市联动end----------------------------------------------
        form.on('submit(salary-submit)',function(){
            $.post("${pageContext.request.contextPath}/SalaryInfo/InsertSalaryInfo",$("#salaryForm").serialize(),function(data){
                if(data=="success"){
                    layer.msg("提交成功！",{icon:1,anim:4,time:2000},function(){
                        //关闭窗口
                        parent.layer.closeAll();
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                        parent.layui.table.reload('tableToReload');
                    });
                }else{
                    layer.msg("提交失败！请重试！",{icon:5,anim:6,time:2000});
                }
            });
        });

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