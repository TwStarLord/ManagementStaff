<%@ page import="com.tw.pojo.Staff" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
		 pageEncoding="utf-8"%>
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
			<div class="layui-card-header">员工信息</div>
			<div class="layui-card-body" style="padding: 15px;">
<%--				测试该界面是否能够获取员工信息1：${editstaffinfo.mail }--%>
<%--				测试该界面是否能够获取员工信息2：${editstaffinfo.departid }--%>
				<form class="layui-form"  lay-filter="component-form-group" id="editstaffForm">
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="x-red">*</span>工号</label>
						<div class="layui-input-block">
							<input type="text" name="jobid" lay-verify="" value="${editstaffinfo.jobid }"
								 placeholder="姓名" class="layui-input">
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="x-red">*</span>姓名</label>
						<div class="layui-input-block">
							<input type="text" name="name" lay-verify="" value="${editstaffinfo.name }"
								 placeholder="姓名" class="layui-input" disabled>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="x-red">*</span>账号</label>
						<div class="layui-input-block">
							<input type="text" name="account" lay-verify="" value="${editstaffinfo.account }"
								 placeholder="账号" class="layui-input" disabled>
						</div>
					</div>
				
				
				<div class="layui-form-item">
            <label class="layui-form-label">部门名称</label>
            <div class="layui-input-block">
              <select name="departid" lay-filter="" id="depaetmentinfo">
              </select>
            </div>
          </div>
          
          <div class="layui-form-item">
            <label class="layui-form-label"><span class="x-red">*</span>单选框</label>
            <div class="layui-input-block">
              <input type="radio" name="sex" value="男" title="男" <c:if test="${editstaffinfo.sex=='男' }">checked="checked"</c:if> >
              <input type="radio" name="sex" value="女" title="女" <c:if test="${editstaffinfo.sex=='女' }">checked="checked"</c:if> >
            </div>
          </div>
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="x-red">*</span>生日</label>
						<div class="layui-input-block">
							<input type="text" name="birthday" lay-verify="title" id="birthday" value="${editstaffinfo.birthday }"
								autocomplete="off" placeholder="生日" class="layui-input" disabled>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="x-red">*</span>学历</label>
						<div class="layui-input-block">
							<select name="eduback"  id="edubackinfo">
								<option value="博士" <c:if test="${editstaffinfo.eduback== '博士' }"> selected="selected" </c:if>  >博士</option>
								<option value="硕士" <c:if test="${editstaffinfo.eduback== '硕士' }"> selected="selected" </c:if> >硕士</option>
								<option value="本科" <c:if test="${editstaffinfo.eduback== '本科' }"> selected="selected" </c:if> >本科</option>
								<option value="其他" <c:if test="${editstaffinfo.eduback== '其他' }"> selected="selected" </c:if> >其他</option>
							</select>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="x-red">*</span>手机</label>
						<div class="layui-input-block">
							<input type="text" name="mobile" lay-verify="mobile" value="${editstaffinfo.mobile }"
								autocomplete="off" placeholder="手机" class="layui-input" disabled>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="x-red">*</span>邮箱</label>
						<div class="layui-input-block"> 
							<input type="text" name="mail" lay-verify="" value="${editstaffinfo.mail }"
								autocomplete="off" placeholder="邮箱" class="layui-input" disabled>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label"><span class="x-red">*</span>地址</label>
						<div class="layui-input-block">
							<input type="text" name="address" lay-verify="title" value="${editstaffinfo.address }"
								autocomplete="off" placeholder="地址" class="layui-input" disabled>
						</div>
					</div>
					<div class="layui-form-item">
						<label class="layui-form-label">入职时间</label>
						<div class="layui-input-block">
							<input type="text" name="timeforjob" lay-verify="title" id="timeforjob" value="${editstaffinfo.timeforjob }"
								autocomplete="off" placeholder="入职时间" class="layui-input">
						</div>
					</div>

					<div class="layui-form-item">
						<label class="layui-form-label">角色信息</label>
						<div class="layui-input-block">
							<input type="text"  lay-verify="title" value="${editstaffinfo.roleList[0].name }"
								   autocomplete="off" placeholder="入职时间" class="layui-input" disabled>
						</div>
					</div>

					
					<div class="layui-form-item">
						<label class="layui-form-label">简介</label>
						<div class="layui-input-block">
							<textarea name="descripetion" placeholder="简介"  value="${editstaffinfo.descripetion }"  class="layui-textarea"></textarea>
						</div>
					</div>
					
         <div class="layui-btn-container" style='text-align:center;'>
					<button class="layui-btn layui-btn-normal layui-btn-radius" type="button" lay-filter="updatestaff-submit" lay-submit="">提交</button>
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
      elem: '#birthday'
    });
    laydate.render({
      elem: '#timeforjob'
    });

    $(function () {
    	var departid = <%=((Staff)request.getAttribute("editstaffinfo")).getDepartid() %>;
		<%--var eduback = <%=((Staff)request.getAttribute("editstaffinfo")).getEduback() %>;--%>
		$.get("${pageContext.request.contextPath}/Department/FindAllDepartmentInfo",function (data,status) {
            var departstr = "";
            // var edubackstr = "";
			$.each(data, function(index,item){
				if (departid == item.departid){
					departstr += "<option value=' "+ item.departid +" '  selected>" + item.departname + "</option>";
				}else{
					departstr += "<option value=' "+ item.departid +" ' >" + item.departname + "</option>";
				}

            })
			// edubackstr += '<option value="博士">博士</option><option value="硕士">硕士</option><option value="本科">本科</option><option value="其他">其他</option>';
			$("#depaetmentinfo").append(departstr);
			// $("#edubackinfo").append(edubackstr);
			//这里需要加上重新渲染，否则数据加载不到select中  坑的yp
			layui.form.render("select");
		})
	})

	  form.on('submit(updatestaff-submit)',function(){
		  $.post("${pageContext.request.contextPath}/StaffInfo/UpdateStaff",$("#editstaffForm").serialize(),function(data){
			  if(data=="success"){
				  layer.msg("注册成功！即将转向登陆页面！",{icon:1,anim:4,time:2000},function(){
					  var index = parent.layer.getFrameIndex(window.name);
					  parent.layer.close(index);
					  parent.layui.table.reload('staffinfo');
				  });
			  }else if (data == "fail"){
				  layer.msg("注册失败！请重试！",{icon:5,anim:6,time:2000});
			  }else{
				  layer.msg("未知错误!",{icon:5,anim:6,time:2000});
			  }

		  });
	  });


  });
  </script>
</body>
</html>