<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>员工注册</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/style/admin.css" media="all">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/style/login.css" media="all">
</head>
<body>
<div class="layadmin-user-login layadmin-user-display-show" id="LAY-user-login" style="display: none;">
    <div class="layadmin-user-login-main">
      <div class="layadmin-user-login-box layadmin-user-login-header layui-col-md12">
        <h2>注册</h2>
      </div>    <%--layadmin-user-login-box layadmin-user-login-body layui-col-md12 --%>
           <form id="regForm" class="layui-form ">
<%--           <div class="layui-form layui-col-md12">--%>
        
        <div class="layui-form-item">
          <label class="layui-form-label">姓名：</label>
          <div class="layui-input-block">
          <input type="text" name="name" id="name" lay-verify="required" placeholder="姓名" class="layui-input">
          </div>
        </div>

        <div class="layui-form-item">
          <label class="layui-form-label">账号：</label>
          <div class="layui-input-block">
          <input type="text" name="account" id="account" lay-verify="required" placeholder="账号(姓名小写字母)" class="layui-input">
          </div>

        </div>
<%--  <div class="layui-form-mid layui-word-aux">该账号已被注册</div>--%>
        <div class="layui-form-item">
          <label class="layui-form-label">密码：</label>
          <div class="layui-input-block">
          <input type="password" name="password" id="password"  lay-verify="required"  placeholder="密码" class="layui-input">
        </div>
        </div>

        <div class="layui-form-item">
          <label class="layui-form-label">确认密码：</label>
          <div class="layui-input-block">
          <input type="password"  id="repassword"   placeholder="确认密码"  lay-verify="required" class="layui-input">
          </div>
        </div>

        
        <div class="layui-form-item">
          <label class="layui-form-label">性别：</label>
          <div class="layui-input-block">
          <input type="radio" name="sex" value="男" title="男" lay-verify="required">
          <input type="radio" name="sex" value="女" title="女" lay-verify="required">
        </div>
        </div>

        <div class="layui-form-item">
          <label class="layui-form-label">出生日期：</label>
          <div class="layui-input-block">
          <input type="text" name="birthday" id="birthday"  lay-verify="date|required"  placeholder="生日" class="layui-input">
        </div>
        </div>


             <div class="layui-form-item">
               <label class="layui-form-label">学历：</label>
               <div class="layui-input-block">
                 <select name="eduback"  id="edubackinfo">
                   <option value="">==学历==</option>
                   <option value="博士">博士</option>
                   <option value="硕士">硕士</option>
                   <option value="本科">本科</option>
                   <option value="其他">其他</option>
                 </select>
             </div>
             </div>
        
        <div class="layui-form-item">
          <label class="layui-form-label">手机：</label>
          <div class="layui-input-block">
          <input type="text" name="mobile" id="mobile" lay-verify="phone" placeholder="手机" class="layui-input">
        </div>
        </div>


        <div class="layui-form-item">
          <label class="layui-form-label">邮箱：</label>
          <div class="layui-input-block">
          <input type="text" name="mail" id="mail" lay-verify="email" placeholder="邮箱" class="layui-input">
        </div>
        </div>


        <div class="layui-form-item">
<%--	    <div class="layui-input-inline layui-form" lay-filter="province">--%>
  <label class="layui-form-label">地址：</label>
  <div class="layui-input-block">
	      <select name="address" id="proData" lay-filter="province" lay-verify="required">
	      </select>
    <select name="address" id="cityData" lay-filter="city" lay-verify="required">
      <option value="">请选择市</option>
    </select>
    <select name="address" id="areaData" lay-verify="required">
      <option value="">请选择县/区</option>
    </select>
  </div>
        </div>
        

        
        <div class="layui-form-item">
          <label class="layui-form-label">简介：</label>
          <div class="layui-input-block">
            <textarea name="descripetion" id="descripetion"  placeholder="简介" class="layui-textarea"></textarea>
          </div>
        </div>

        <div class="layui-trans layui-form-item layadmin-user-login-other">
          <a href='<c:url value='/login.jsp' />' class="layadmin-user-jump-change layadmin-link layui-hide-xs">用已有帐号登入</a>
          <button class="layui-btn layui-btn-fluid" lay-submit="" type="button" lay-filter="reg-submit">注 册</button>
        </div>
      </form>
    </div>

  </div>

</body>

<script src="${pageContext.request.contextPath}/layui/layuiadmin/layui/layui.js"></script>
<script src="${pageContext.request.contextPath}/layui/layuiadmin/layui/lay/modules/jquery.js"></script>
  <script type="text/javascript">
 var form;
 var layer;
 var $;
		  layui.use(['form','layer','jquery','laydate'],function(){
				var form = layui.form,
				layer=layui.layer,
				$ = layui.$,
                laydate = layui.laydate;

				var flag = true;


            laydate.render({
              elem: '#birthday'
            });

				//设置页面加载时寻找到省级名称，进行省级联动
				$(function() {
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

            // 账号检测
				$("#account").on('blur',function(){
				  var that = this;
					if($("#account").val()!=null && $("#account").val() != ''){
				$.ajax({
                      url:"${pageContext.request.contextPath}/Login/CheckAccount",
                      data:{'account':$("#account").val()},
                      type:"post",
                      success:function(data){
                    	  if(data == "fail"){//请求成功且该账号没有被注册
                            // layer.msg("账号已经被注册了!");
                            layer.tips('账号已经被注册了!', that, {tips: 1,time:1500});
                            flag = false;
                    	  }else {
                              flag = true;
                          }
                      },
                      error:function(data){
                          layer.msg("请求失败!",{icon:5,anim:6,time:1500});
                      }
                  });
					}
				});
            // 邮箱检测
            $("#mail").on('blur',function(){
              var that = this;
              if($("#mail").val()!=null && $("#mail").val() != ''){
                $.ajax({
                  url:"${pageContext.request.contextPath}/Login/CheckMail",
                  data:{'account':$("#mail").val()},
                  type:"post",
                  success:function(data){
                    if(data == "fail"){//请求成功且该账号没有被注册
                      layer.tips('邮箱已经被注册了!', that, {tips: 1,time:1500});
                      flag = false;
                    }else {
                        flag = true;
                    }
                  },
                  error:function(data){
                    layer.msg("请求失败!",{icon:5,anim:6,time:1500});
                  }
                });
              }
            });
				
				$("#password").on('blur',function(){
				  var that = this;
					var pval = $("#password");
					if(pval.val()==''|| pval.val()==null){
                      layer.tips('密码不能为空!', that, {tips: 1,time:1500});
						flag = false;
					}else {
                        flag = true;
                    }
				});
				
				$("#repassword").on('blur',function(){
                  var that = this;
					if($("#password").val() != $("#repassword").val()){
                      layer.tips('密码不匹配!', that, {tips: 1,time:1500});
						flag = false;
					}		else {
                        flag = true;
                    }
					});

				form.on('submit(reg-submit)',function(){
				  if(flag){
                    $.post("${pageContext.request.contextPath}/Login/Regist",$("#regForm").serialize(),function(data){
                      if(data=="success"){
                        layer.msg("注册成功！即将转向登陆页面！",{icon:1,anim:4,time:1500},function(){
                          window.location.href="<c:url value='/login.jsp' />";
                        });
                      }else{
                        layer.msg("注册失败！请重试！",{icon:5,anim:6,time:1500});
                      }
                    });
                  }else{
                    layer.msg("请修改信息后提交！",{icon:5,anim:6,time:1500});
                  }

				});
				
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
				});
	</script>
</html>