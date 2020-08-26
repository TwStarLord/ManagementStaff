<%@ page import="com.tw.pojo.Qingjia" %>
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
			<div class="layui-card-header">员工请假信息</div>
			<div class="layui-card-body" style="padding: 15px;">
				<form id="QingjiaForm" class="layui-form"
					lay-filter="component-form-group">

					<div class="layui-form-item">
						<label class="layui-form-label"><span class="x-red">*</span>序号</label>
						<div class="layui-input-block">
							<input type="text" name="id" value="${editqingjiainfo.id }"
								   placeholder="工号" class="layui-input" readonly="readonly">
						</div>
					</div>

					<div class="layui-form-item">
						<label class="layui-form-label"><span class="x-red">*</span>工号</label>
						<div class="layui-input-block">
							<input type="text" name="jobid" value="${editqingjiainfo.jobid }"
								placeholder="工号" class="layui-input" readonly="readonly">
						</div>
					</div>

					<div class="layui-form-item">
						<label class="layui-form-label"><span class="x-red">*</span>姓名</label>
						<div class="layui-input-block">
							<input type="text" name="name" value="${editqingjiainfo.name }"
								placeholder="姓名" class="layui-input" readonly="readonly">
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
						<label class="layui-form-label">开始时间</label>
						<div class="layui-input-block">
							<input type="text" name="starttime" lay-verify="required"
								id="starttime" value="${editqingjiainfo.starttime }"
								autocomplete="off" placeholder="开始时间" class="layui-input">
						</div>
					</div>

					<div class="layui-form-item">
						<label class="layui-form-label">结束时间</label>
						<div class="layui-input-block">
							<input type="text" name="endtime" lay-verify="required"
								id="endtime" value="${editqingjiainfo.endtime }"
								autocomplete="off" placeholder="结束时间" class="layui-input">
						</div>
					</div>
					<input type="hidden" id="statusValue" value="${editqingjiainfo.status}">
					<div class="layui-form-item">
						<label class="layui-form-label">状态</label>
						<div class="layui-input-block">
							<select name="status" id="status" lay-verify="required">
							</select>
						</div>
					</div>

					<div class="layui-form-item">
						<label class="layui-form-label"><span class="x-red">*</span>请假事由</label>
						<div class="layui-input-block">
							<input type="text" name="cause" lay-verify="title"
								value="${editqingjiainfo.cause }" autocomplete="off"
								placeholder="请假事由" class="layui-input">
						</div>
					</div>

					<div class="layui-form-item"><%--readonly="readonly"--%>
						<label class="layui-form-label">简介</label>
						<div class="layui-input-block">
							<textarea name="descripetion"
								placeholder="简介" value="" readonly="readonly"
								class="layui-textarea">${editqingjiainfo.descripetion }</textarea>
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

		laydate.render({
		elem : '#starttime'
		});
		laydate.render({
		elem : '#endtime'
		});

			$(function () {
				var status = $("#statusValue").val();
				var optionstr = "";
				if (status == "待审核"){
					optionstr += '<option value="待审核" selected>待审核</option><option value="同意">同意</option><option value="不同意">不同意</option>'
				}else if (status == "同意"){
					optionstr += '<option value="待审核">待审核</option><option value="同意" selected>同意</option><option value="不同意">不同意</option>'
				}else if (status == "不同意"){
					optionstr += '<option value="待审核">待审核</option><option value="同意">同意</option><option value="不同意" selected>不同意</option>'
				}
				$("#status").append(optionstr);

				var departid = <%=((Qingjia)request.getAttribute("editqingjiainfo")).getDepartid() %>;
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
			$.post("${pageContext.request.contextPath}/Qingjia/UpdateQingjiaInfo",$("#QingjiaForm").serialize(),function(data) {//经过serialize之后用console打印看下传递数据
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