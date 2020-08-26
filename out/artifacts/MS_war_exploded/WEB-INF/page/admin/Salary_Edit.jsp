<%@ page import="com.tw.pojo.Staff" %>
<%@ page import="com.tw.pojo.Salary" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="utf-8">
    <title>薪资编辑</title>
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
        <div class="layui-card-header">员工薪资信息编辑</div>
        <div class="layui-card-body" style="padding: 15px;">
            <form id="salaryForm" class="layui-form"
                  lay-filter="component-form-group">

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>序号</label>
                    <div class="layui-input-block">
                        <input type="text" name="id" value="${editsalaryinfo.id }"
                               placeholder="序号" class="layui-input" readonly="readonly">
                    </div>
                </div>



                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>工号</label>
                    <div class="layui-input-block">
                        <input type="text" name="jobid" value="${editsalaryinfo.jobid }"
                               placeholder="工号" class="layui-input" disabled>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>姓名</label>
                    <div class="layui-input-block">
                        <input type="text" name="name" value="${editsalaryinfo.name }"
                               placeholder="姓名" class="layui-input" disabled>
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">部门名称</label>
                    <div class="layui-input-block">
                        <select name="department.departid" lay-filter="" id="depaetmentinfo" disabled>
                        </select>
                    </div>
                </div>


                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>结算月份</label>
                    <div class="layui-input-block">
                        <select name="jiesuanyuefen" id="monthSelect" lay-verify="required">
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
                    <label class="layui-form-label">基本工资</label>
                    <div class="layui-input-block">
                        <input type="text" name="jibengongzi" lay-verify="required"
                               id="jibengongzi" value="${editsalaryinfo.jibengongzi }"
                               id="jibengongzi" value="${editsalaryinfo.jibengongzi }"
                               autocomplete="off" placeholder="基本工资" class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label">奖金</label>
                    <div class="layui-input-block">
                        <input type="text" name="jiangli" lay-verify=""
                               value="${editsalaryinfo.jiangli }" autocomplete="off"
                               placeholder="奖金" class="layui-input">
                    </div>
                </div>

                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>惩金</label>
                    <div class="layui-input-block">
                        <input type="text" name="chengfa" lay-verify="title"
                               value="${editsalaryinfo.chengfa }" autocomplete="off"
                               placeholder="惩金" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>加班工资</label>
                    <div class="layui-input-block">
                        <input type="text" name="jiabangongzi" lay-verify="title"
                               value="${editsalaryinfo.jiabangongzi }" autocomplete="off"
                               placeholder="加班工资" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>旷工工资</label>
                    <div class="layui-input-block">
                        <input type="text" name="kuanggonggongzi" lay-verify="title"
                               value="${editsalaryinfo.kuanggonggongzi }" autocomplete="off"
                               placeholder="旷工工资" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>请假工资</label>
                    <div class="layui-input-block">
                        <input type="text" name="qingjiagongzi" lay-verify="title"
                               value="${editsalaryinfo.qingjiagongzi }" autocomplete="off"
                               placeholder="请假工资" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>出差工资</label>
                    <div class="layui-input-block">
                        <input type="text" name="chuchaigongzi" lay-verify="title"
                               value="${editsalaryinfo.chuchaigongzi }" autocomplete="off"
                               placeholder="出差工资" class="layui-input">
                    </div>
                </div>
                <div class="layui-form-item">
                    <label class="layui-form-label"><span class="x-red">*</span>实际结算</label>
                    <div class="layui-input-block">
                        <input type="text" name="shijijiesuan" lay-verify="title"
                               value="${editsalaryinfo.shijijiesuan }" autocomplete="off"
                               placeholder="实际结算" class="layui-input">
                    </div>
                </div>


                <div class="layui-btn-container" style='text-align: center;'>
                    <button type="button"
                            class="layui-btn layui-btn-normal layui-btn-radius" lay-submit=""
                            lay-filter="editSubmit">提交</button>
                    <input type="reset"
                           class="layui-btn layui-btn-warm layui-btn-radius">重置
                    </button>

                </div>

            </form>
        </div>
    </div>
</div>
<script
        src="${pageContext.request.contextPath}/layui/layuiadmin/layui/layui.js"></script>
<script>
    layui.config({base : '${pageContext.request.contextPath}/layui/layuiadmin/' //静态资源所在路径
    }).extend({index : 'lib/index' //主入口模块
    }).use([ 'index', 'form', 'laydate' ],
        function() {
            var $ = layui.$,
                admin = layui.admin,
                element = layui.element,
                layer = layui.layer,
                laydate = layui.laydate,
                form = layui.form;

            $(function () {
                var departid = <%=((Salary)request.getAttribute("editsalaryinfo")).getDepartid() %>;
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
                $.post("${pageContext.request.contextPath}/SalaryInfo/UpdateSalaryInfo",$("#salaryForm").serialize(),function(data) {//经过serialize之后用console打印看下传递数据
                    if (data == "success") {
                        layer.msg("更新成功!",{icon : 1,anim : 4,time : 1500},function() {//弹出成功之后等等待两秒窗口关闭
                            var index = parent.layer.getFrameIndex(window.name);
                            parent.layer.close(index);
                            parent.layui.table.reload('tableToReload');
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