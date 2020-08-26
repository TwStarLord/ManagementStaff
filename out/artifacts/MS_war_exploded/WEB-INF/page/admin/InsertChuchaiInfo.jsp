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
			<div class="layui-card-header">添加出差信息</div>
			<div class="layui-card-body" style="padding: 15px;">
				<form class="layui-form" id="chuchaiForm" lay-filter="component-form-group">
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

					<input type="hidden" value="" name="account" id="account">

					<div class="layui-form-item">
						<label class="layui-form-label">部门编号</label>
						<div class="layui-input-block">
							<select name="departid" lay-filter="" id="depaetmentinfo">
							</select>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="x-red">*</span>开始时间</label>
						<div class="layui-input-block">
							<input type="text" name="starttime" lay-verify="required" id="starttime"
								autocomplete="off" placeholder="开始时间" class="layui-input">
						</div>
					</div>
					
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="x-red">*</span>结束时间</label>
						<div class="layui-input-block">
							<input type="text" name="endtime" lay-verify="required" id="endtime"
								autocomplete="off" placeholder="结束时间" class="layui-input">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="x-red">*</span>目的地</label>
						<div class="layui-input-block">
							<input type="text" name="destination" lay-verify="required" 
								autocomplete="off" placeholder="目的地" class="layui-input">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="x-red">*</span>状态</label>
						<div class="layui-input-block"> 
							<input type="text" name="status" lay-verify="required" value="未确认"
								autocomplete="off" placeholder="状态" class="layui-input" readonly>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="x-red">*</span>事由</label>
						<div class="layui-input-block">
							<input type="text" name="cause" lay-verify="required" 
								autocomplete="off" placeholder="事由" class="layui-input">
						</div>
					</div>
					
         <div class="layui-btn-container" style='text-align:center;'>
					<button type="button" class="layui-btn layui-btn-normal layui-btn-radius" lay-submit="" lay-filter="chuchai-submit">提交</button>
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
	  form.on('submit(chuchai-submit)',function(){
		  $.post("${pageContext.request.contextPath}/Chuchai/InsertChuchaiInfo",$("#chuchaiForm").serialize(),function(data){
			  if(data=="success"){
				  layer.msg("提交成功！",{icon:1,anim:4,time:2000},function(){
					  //关闭窗口
					  parent.layer.closeAll();
					  var index = parent.layer.getFrameIndex(window.name);
					  parent.layer.close(index);
					  parent.layui.table.reload('tableToReload');
				  });
			  }else if (data == "alreadyHasChuchaiInfo"){
				  layer.msg("该员工在该时间段已经有出差安排了！",{icon:5,anim:6,time:1500});
			  } else{
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
				  $("#account").val(data.account);
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