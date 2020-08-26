<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>设置我的资料</title>
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
<div class="layui-row layui-col-space15">
<div class="layui-col-md12">
<div class="layui-card">
<div class="layui-card-header">设置我的资料</div>
<div class="layui-card-body" pad15>

	<div class="layui-form" lay-filter="">
		<form class="layui-form" id="basicinfoForm">

			<input type="hidden" name="jobid" value="${staffinfo.jobid }" id="jobid">

			<div class="layui-form-item">
				<label class="layui-form-label">我的角色</label>
				<div class="layui-input-inline">
					<select name="role" lay-verify="">
						<option value="${staffinfo.roleList[0].id}" selected disabled>${staffinfo.roleList[0].name}</option>
					</select>
				</div>
				<div class="layui-form-mid layui-word-aux">当前角色不可更改为其它角色</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">姓名</label>
				<div class="layui-input-inline">
					<input type="text" name="name" value="${staffinfo.name }"
						readonly class="layui-input" disabled="disabled">
				</div>

			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">账号</label>
				<div class="layui-input-inline">
					<input type="text" name="nickname"
						value="${staffinfo.account }" lay-verify="account"
						autocomplete="off" placeholder="请输入昵称" class="layui-input"
						disabled="disabled">
				</div>
				<div class="layui-form-mid layui-word-aux">不可修改,登录账号!</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">性别</label>
				<div class="layui-input-block">
					<input type="radio" name="sex" value="男" title="男"
						<c:if test="${staffinfo.sex eq '男' }">checked</c:if>
						disabled="disabled"> <input type="radio" name="sex"
						value="女" title="女"
						<c:if test="${staffinfo.sex eq '女' }">checked</c:if>
						disabled="disabled">
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label" >上传头像</label>
				<div class="layui-input-inline">
					<div class="layui-input-inline">
						<button type="button" class="layui-btn" id="imguploadbtn">上传图片</button>
					</div>
				</div>
			</div>

			<div class="layui-form-item">
				<label  class="layui-form-label">头像预览</label>
				<div class="layui-input-inline">
					<img class="layui-upload-img"   style="width: 100px; height: 100px; margin: 0 10px 10px 0;"  id="imageurl">
				</div>
			</div>

			<input type="hidden" name="image" id="image" value="">

			<div class="layui-form-item">
				<label class="layui-form-label">手机</label>
				<div class="layui-input-inline">
					<input type="text" name="mobile" value="${staffinfo.mobile }" lay-verify="phone"
						autocomplete="off" class="layui-input">
				</div>
			</div>

			<%--<div class="layui-form-item">
				<label class="layui-form-label">地址</label>
				<div class="layui-input-inline">
					<input type="text" name="address" value=""
						lay-verify="required" autocomplete="off" class="layui-input">
				</div>
			</div>--%>

			<div class="layui-form-item">
				<label class="layui-form-label">地址</label>
				<div class="layui-input-inline layui-form" lay-filter="province">
					<select name="address" id="proData" lay-filter="province" required lay-verify="required">
					</select>
				</div>
				<div class="layui-input-inline layui-form" lay-filter="city">
					<select name="address" id="cityData" lay-filter="city" required lay-verify="required">
						<option value="">请选择市</option>
					</select>
				</div>
				<div class="layui-input-inline layui-form" lay-filter="area">
					<select name="address" id="areaData" required lay-verify="required">
						<option value="">请选择县/区</option>
					</select>
				</div>
			</div>

			<div class="layui-form-item">
				<label class="layui-form-label">邮箱</label>
				<div class="layui-input-inline">
					<input type="text" name="mail" value="${staffinfo.mail }" id="mail"
						lay-verify="email" autocomplete="off" placeholder="邮箱" class="layui-input">
				</div>
				<c:if test="${staffinfo.activecode == null || staffinfo == '' }">
					<button class="layui-btn layui-btn-sm" id="sendmail"><i class="layui-icon"></i></button>
				</c:if>

			</div>

			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label">简介</label>
				<div class="layui-input-block">
					<textarea name="descripetion"
						value="${staffinfo.descripetion }" placeholder="请输入内容"
						class="layui-textarea"></textarea>
				</div>
			</div>

			<div class="layui-form-item">
				<div class="layui-input-block">
					<button type="button" class="layui-btn" lay-submit="" lay-filter="basicinfo-submit">确认修改</button>
					<button type="reset" class="layui-btn layui-btn-primary">重新填写</button>
				</div>
			</div>
		</form>
	</div>
</div>
</div>
</div>
</div>
</div>

<script
src="${pageContext.request.contextPath}/layui/layuiadmin/layui/layui.all.js"></script>
<script>
		layui.use(['upload','jquery','form','layer'], function(){
		var $ = layui.jquery,
		upload = layui.upload,
		form = layui.form,
		layer = layui.layer;

		form.on('submit(basicinfo-submit)',function(){
				$.post("${pageContext.request.contextPath}/StaffInfo/UpdateStaffSelfInfo",$("#basicinfoForm").serialize(),function(data){
					if(data=="success"){
						layer.msg("修改成功！",{icon:1,anim:4,time:2000});
					}else{
						layer.msg("修改失败！请重试！",{icon:5,anim:6,time:2000});
					}
				});
			});

		$("#sendmail").on("click",function (data) {
			var mailValue = $("#mail").val();
			var jobidValue = $("#jobid").val();
			$.ajax({
				url:"${pageContext.request.contextPath}/Mail/sendMail",
				type:"post",
				data:{mail:mailValue,jobid:jobidValue},
				success:function (data) {
					if(data == "success"){
						layer.msg('发送成功!请查收并激活!');
					}else{
						layer.msg('发送失败!请检查邮箱格式或者重新发送！');
					}
				},
				error:function (error) {
					layer.msg('请求失败，请重新尝试!');
				}
			})
		})

		$(function () {
			showAllProvince();
		})

			//加载省
			function showAllProvince(){
				$.ajax({
					type:"post",
					url:"${pageContext.request.contextPath}/findAllProvinces",
					success:function(data){
						var str="<option value=''>请选择省</option>"
						for(var i=0;i<data.length;i++){
							str=str+"<option value='"+data[i].provinceId+"'>"+data[i].provinceName+"</option>"
						}
						$("#proData").html(str);
						form.render();
					}
				});
			}


			//省市区联动
			form.on('select(province)',function(data){
				$("#areaData").empty();
				$.ajax({
					type:"post",
					url:"${pageContext.request.contextPath}/findCityByProId",
					data:"provinceId="+data.value,
					success:function(data){
						var str="<option value=''>请选择市</option>"
						for(var i=0;i<data.length;i++){
							str=str+"<option value='"+data[i].cityId+"'>"+data[i].cityName+"</option>";
						}
						$("#cityData").html(str);
						form.render();
					}
				});
			});
			form.on('select(city)',function(data){
				$.ajax({
					type:"post",
					url:"${pageContext.request.contextPath}/findAreaByCityId",
					data:"cityId="+data.value,
					success:function(data){
						var str="<option value=''>请选择县/区</option>"
						for(var i=0;i<data.length;i++){
							str=str+"<option value='"+data[i].areaId+"'>"+data[i].areaName+"</option>";
						}
						$("#areaData").html(str);
						form.render();
					}
				});
			});



		//普通图片上传
		var uploadInst = upload.render({
		elem: '#imguploadbtn'
		,url: '${pageContext.request.contextPath}/upload/geturl'//调试不开启端口
		,accept:'images'
		,size:50000
		,before: function(obj){
		obj.preview(function(index, file, result){
		// 默认异步上传后将服务器中图片地址返回
		$('#imageurl').attr('src', result);
		});
		}
		,done: function(res){
		//如果上传失败
		if(res.code > 0){
		return layer.msg('上传失败');
		}
		//上传成功
		var demoText = $('#demoText');
		demoText.html('<span style="color: #4cae4c;">上传成功</span>');

		var fileupload = $("#image");
		console.log(fileupload);
		fileupload.attr("value",res.data.src);
		}
		,error: function(){
		//演示失败状态，并实现重传
		var demoText = $('#demoText');
		demoText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
		demoText.find('.demo-reload').on('click', function(){
		uploadInst.upload();
		});
		}
		});
		});
</script>
</body>
</html>