<%@ page import="com.tw.pojo.Staff" %>
<%@ page import="com.tw.pojo.Chuchai" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>出差信息编辑</title>
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
			<div class="layui-card-header">员工出差信息</div>
			<div class="layui-card-body" style="padding: 15px;">
				<form class="layui-form"  id="ChuchaiForm" lay-filter="component-form-group">
					
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="x-red">*</span>工号</label>
						<div class="layui-input-block">
							<input type="text" name="jobid" value="${editchuchaiinfo.jobid }"
								 placeholder="工号" class="layui-input" readonly="readonly">
						</div>
					</div>
					
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="x-red">*</span>姓名</label>
						<div class="layui-input-block">
							<input type="text" name="name" value="${editchuchaiinfo.name }"
								 placeholder="姓名" class="layui-input" readonly="readonly">
						</div>
					</div>
				
				
				<div class="layui-form-item">
            <label class="layui-form-label">部门编号</label>
            <div class="layui-input-block">
              <select name="departid" lay-filter="">
                <option>==请选择==</option>
                <option value="1" <c:if test="${editchuchaiinfo.departid==1 }"> selected</c:if>>1</option>
                <option value="2" <c:if test="${editchuchaiinfo.departid==2 }"> selected</c:if>>2</option>
                <option value="3" <c:if test="${editchuchaiinfo.departid==3 }"> selected</c:if>>3</option>
                <option value="4" <c:if test="${editchuchaiinfo.departid==4 }"> selected</c:if>>4</option>
              </select>
            </div>
          </div>

					<div class="layui-form-item">
						<label class="layui-form-label">部门名称</label>
						<div class="layui-input-block">
							<select name="department.departid" lay-filter="" id="depaetmentinfo">
							</select>
						</div>
					</div>


					<div class="layui-form-item">
						<label class="layui-form-label">开始时间</label>
						<div class="layui-input-block">
							<input type="text" name="starttime" lay-verify="required" id="starttime" value="${editchuchaiinfo.starttime }"
								autocomplete="off" placeholder="开始时间" class="layui-input"> 
						</div>
					</div>
					
					<div class="layui-form-item">
						<label class="layui-form-label">结束时间</label>
						<div class="layui-input-block">
							<input type="text" name="endtime" lay-verify="required" id="endtime" value="${editchuchaiinfo.endtime }"
								autocomplete="off" placeholder="结束时间" class="layui-input">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">目的地</label>
						<div class="layui-input-block">
							<input type="text" name="destination" lay-verify="required" value="${editchuchaiinfo.destination }"
								autocomplete="off" placeholder="目的地" class="layui-input">
						</div>
					</div>
					
					<div class="layui-form-item">
						<label class="layui-form-label">状态</label>
						<div class="layui-input-block"> 
							<input type="text" name="status" lay-verify="" value="${editchuchaiinfo.status }"
								autocomplete="off" placeholder="状态" class="layui-input">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="x-red">*</span>事由</label>
						<div class="layui-input-block">
							<input type="text" name="cause" lay-verify="title" value="${editchuchaiinfo.cause }"
								autocomplete="off" placeholder="地址" class="layui-input" >
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="x-red">*</span>审批人</label>
						<div class="layui-input-block">
							<input type="text" name="shenpi" lay-verify="title" value="${login_name }"
								autocomplete="off" placeholder="地址" class="layui-input" readonly="readonly">
						</div>
					</div>
					
         <div class="layui-btn-container" style='text-align:center;'>
					<button type="submit" lay-submit="" class="layui-btn layui-btn-normal layui-btn-radius" lay-filter="editSubmit">提交</button>
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

    laydate.render({
      elem: '#starttime'
    });
    laydate.render({
      elem: '#endtime'
    });


	  $(function () {
		  var departid = <%=((Chuchai)request.getAttribute("editchuchaiinfo")).getDepartid() %>;
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
	  });

	  form.on('submit(editSubmit)',function() {
		  $.post("${pageContext.request.contextPath}/Chuchai/UpdateChuchai",$("#ChuchaiForm").serialize(),function(data) {//经过serialize之后用console打印看下传递数据
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