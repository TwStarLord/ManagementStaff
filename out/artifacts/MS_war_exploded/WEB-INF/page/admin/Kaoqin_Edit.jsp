<%@ page import="com.tw.pojo.Staff" %>
<%@ page import="com.tw.pojo.Kaoqin" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <title>考勤信息编辑</title>
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
        <div class="layui-card-header">员工考勤信息</div>
        <div class="layui-card-body" style="padding: 15px;">
            <form id="QingjiaForm" class="layui-form"
                  lay-filter="component-form-group">

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>序号</label>
                    <div class="layui-input-block">
                        <input type="text" name="id" value="${editkaoqininfo.id }"
                               placeholder="工号" class="layui-input" readonly="readonly">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>工号</label>
                    <div class="layui-input-block">
                        <input type="text" name="jobid" value="${editkaoqininfo.jobid }"
                               placeholder="工号" class="layui-input" readonly>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>姓名</label>
                    <div class="layui-input-block">
                        <input type="text" name="name" value="${editkaoqininfo.name }"
                               placeholder="姓名" class="layui-input" readonly="readonly">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">部门名称</label>
                    <div class="layui-input-block">
                        <select name="departid" lay-filter="" id="depaetmentinfo" readonly="readonly">
                        </select>
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label">打卡时间</label>
                    <div class="layui-input-block">
                        <input type="text" name="recordtime" lay-verify="required"
                               id="recordtime" value="${editkaoqininfo.recordtime }"
                               autocomplete="off" placeholder="打卡时间" class="layui-input" readonly>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">状态</label>
                    <div class="layui-input-block">
                        <input type="text" name="checkstatus" lay-verify="required"
                               id="checkstatus" value="${editkaoqininfo.checkstatus }"
                               autocomplete="off" placeholder="状态 0正常 1反常 " class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">月份</label>
                    <div class="layui-input-block">
                        <input type="text" name="month" lay-verify=""
                               value="${editkaoqininfo.month }" autocomplete="off"
                               placeholder="状态" class="layui-input" readonly>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>正常上班</label>
                    <div class="layui-input-block">
                        <input type="text" name="zhengchangshangban" lay-verify="title"
                               value="${editkaoqininfo.zhengchangshangban }" autocomplete="off"
                               placeholder="请假事由" class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>加班时间</label>
                    <div class="layui-input-block">
                        <input type="text" name="jiabantime" lay-verify="title"
                               value="${editkaoqininfo.jiabantime }" autocomplete="off"
                               placeholder="请假事由" class="layui-input">
                    </div>
                </div> <div class="layui-form-item">
                <label class="layui-form-label"><span class="x-red">*</span>请假天数</label>
                <div class="layui-input-block">
                    <input type="text" name="qingjiaday" lay-verify="title"
                           value="${editkaoqininfo.qingjiaday }" autocomplete="off"
                           placeholder="请假事由" class="layui-input">
                </div>
            </div> <div class="layui-form-item">
                <label class="layui-form-label"><span class="x-red">*</span>出差天数</label>
                <div class="layui-input-block">
                    <input type="text" name="chuchaiday" lay-verify="title"
                           value="${editkaoqininfo.chuchaiday }" autocomplete="off"
                           placeholder="请假事由" class="layui-input">
                </div>



                <div class="layui-btn-container" style='text-align: center;'>
                    <button type="button"
                            class="layui-btn layui-btn-normal layui-btn-radius" lay-submit=""
                            lay-filter="editSubmit">提交</button>
                    <input type="reset"
                           class="layui-btn layui-btn-warm layui-btn-radius">重置
                    </button>

                </div>


        </div>
            </form>
    </div>
</div>
<script
        src="${pageContext.request.contextPath}/layui/layuiadmin/layui/layui.js"></script>
<script>
    layui.config({base : '${pageContext.request.contextPath}/layui/layuiadmin/' //静态资源所在路径
    }).extend({index : 'lib/index' //主入口模块
    }).use([ 'index', 'form'],
        function() {
            var $ = layui.$,
                admin = layui.admin,
                element = layui.element,
                layer = layui.layer,
                form = layui.form;

            $(function () {
                var departid = <%=((Kaoqin)request.getAttribute("editkaoqininfo")).getDepartid() %>;
                $.get("${pageContext.request.contextPath}/Department/FindAllDepartmentInfo",function (data,status) {
                    var departstr = "";
                    $.each(data, function(index,item){
                        if (departid == item.departid){
                            departstr += "<option value=' "+ item.departid +" '  selected>" + item.departname + "</option>";
                        }else{
                            departstr += "<option value=' "+ item.departid +" ' >" + item.departname + "</option>";
                        }

                    })
                    $("#depaetmentinfo").append(departstr);
                    //这里需要加上重新渲染，否则数据加载不到select中  坑的yp
                    layui.form.render("select");
                })
            })

            form.on('submit(editSubmit)',function() {
                $.post("${pageContext.request.contextPath}/SalaryInfo/UpdateSalaryInfo",$("#QingjiaForm").serialize(),function(data) {//经过serialize之后用console打印看下传递数据
                    if (data == "success") {
                        layer.msg("更新成功!",{icon : 1,anim : 4,time : 1500},function() {//弹出成功之后等等待两秒窗口关闭
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            parent.layui.table.reload('allKaoqinInfo');
                        });
                    } else {
                        layer.msg("更新失败!",{icon : 5,anim : 6,time : 2000
                        });
                    }
                });
            });
        });
</script>
</body>
</html>