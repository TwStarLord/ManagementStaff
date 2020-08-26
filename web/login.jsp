<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>xxx公司管理系统</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/images/favicon.ico" type="image/x-icon" />
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/style/login.css" media="all">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/style/admin.css" media="all">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/layui/layuiadmin/layui/css/layui.css" media="all">
</head>

<body>

  <div class="layadmin-user-login layadmin-user-display-show" id="LAY-user-login" style="display: none;">

    <div class="layadmin-user-login-main">
      <div class="layadmin-user-login-box layadmin-user-login-header">
        <h2>xxx公司</h2>
        <p>员工管理系统</p>
      </div>
      <%-- <span style="color: red; font-size: 10pt; font-weight: 900;"> ${error.msg }</span> --%>
      <form  id="loginForm" class="layui-form" action="${pageContext.request.contextPath}/Login/login" method="post">
      <div class="layadmin-user-login-box layadmin-user-login-body layui-form">
        <div class="layui-form-item">
          <label class="layadmin-user-login-icon layui-icon layui-icon-username" for="LAY-user-login-username"></label>
          <input type="text" name="account" autocomplete="on" id="account"  placeholder="用户名" class="layui-input">
        </div>
        <div class="layui-form-item">
          <label class="layadmin-user-login-icon layui-icon layui-icon-password" for="LAY-user-login-password"></label>
          <input type="password" autocomplete="on" name="password" id="password"  placeholder="密码" class="layui-input">
        </div>
        
        <div class="layui-form-item">
			<div class="layui-row">
							<!-- 验证码 -->
				<div class="layui-col-xs7">
				<label class="layadmin-user-login-icon layui-icon layui-icon-vercode" for="LAY-user-login-vercode"></label> 
				<input type="text" id="verifyCode"  lay-verify="" placeholder="图形验证码" class="layui-input">
				<span style="color: red; font-size: 10pt; font-weight: 900;">${error.vcode }</span>
				</div>
				<img id="vCode" src="<c:url value='/getVerifyCode'/>"border="2" /> <a href="javascript:_change()">换一张</a>
            </div>
		</div>
		
		
        <div class="layui-form-item" style="margin-bottom: 20px;">
          <input type="checkbox" checked="checked"  name="remember" lay-skin="primary" title="记住密码">
<%--          <a href="forget.html" class="layadmin-user-jump-change layadmin-link" style="margin-top: 7px;">忘记密码？</a>--%>
        </div>
        <div class="layui-form-item">
          <button class="layui-btn layui-btn-fluid" type="button" id="login" lay-submit lay-filter="login">登 入</button>
        </div>
        <div class="layui-trans layui-form-item layadmin-user-login-other">
          <a href="${pageContext.request.contextPath}/regist.jsp" class="layadmin-user-jump-change layadmin-link">注册帐号</a>
        </div>
      </div>
      </form>
    </div>
  </div>
  
<script type="text/javascript">
				/*
				如果一个表单项的name和<img>的id相同，那么可能会出问题！一般只有IE出问题！
				 */
				function _change() {
					/*
					1. 获取<img>元素，name和id相同的话可能会导致getnamebyid被提前设置了一个跟name同名的id值。
					 */
					var ele = document.getElementById("vCode");
					ele.src = "<c:url value='/getVerifyCode'/>?xxx=" + new Date().getTime();
				}
</script>

<script src="${pageContext.request.contextPath}/layui/layuiadmin/layui/layui.js"></script>
  <script>
  layui.config({
	    base: '${pageContext.request.contextPath}/layui/layuiadmin/' //静态资源所在路径
	  }).extend({
	    index: 'lib/index' //主入口模块
	  }).use(['form','jquery','layer'], function(){
  var $ = layui.jquery,
  form = layui.form,
  layer = layui.layer;

  var result = null;
  var accountValue = null;
  var pwdValue = null;
  var verifyCodeValue = null;

      $("#login").on("click",function () {

          accountValue = $("#account").val();
          pwdValue = $("#password").val();
          verifyCodeValue = $("#verifyCode").val();

          // console.log(accountValue == null || accountValue == "");
          // console.log(pwdValue == null || pwdValue == "");
          // console.log(verifyCodeValue == null || verifyCodeValue == "");

          if (accountValue == null || accountValue == ""){
              layer.msg("用户名不能为空!",{icon : 5,anim : 6,time : 2000});
          }else{
              if (pwdValue == null || pwdValue == ""){
                  layer.msg("密码不能为空!",{icon : 5,anim : 6,time : 2000});
              }else {
                  if(verifyCodeValue == null || verifyCodeValue == ""){
                      layer.msg("验证码不能为空!",{icon : 5,anim : 6,time : 2000});
                  }else {
                    $.ajax({//优先验证验证码
                      url:"${pageContext.request.contextPath}/Login/checkVerifyCode",
                      type:"POST",
                      data:{verifyCode:verifyCodeValue},
                      success:function (data) {
                        if (data == "success"){//验证码成功，进行身份验证
                          $.ajax({
                            url:"${pageContext.request.contextPath}/Login/login",
                            data:($("form#loginForm").serialize()),
                            type:"post",
                            success:function (data) {
                              result = JSON.parse(data);
                              /*if(result.data == "verifyCodeError"){
                                  layer.msg("验证码错误!",{icon : 5,anim : 6,time : 2000});
                              }else */if (result.data == "accountError"){
                                layer.msg("账号不存在!",{icon : 5,anim : 6,time : 2000});
                              }else if (result.data == "pwdError"){
                                layer.msg("账号或密码错误!",{icon : 5,anim : 6,time : 2000});
                              }else if (result.data == "otherError"){
                                layer.msg("未知错误!",{icon : 5,anim : 6,time : 2000});
                              }else if (result.data == "success"){
                                window.location.href="${pageContext.request.contextPath}/Login/ToIndex";
                              }
                            },
                            error:function (ex) {
                              layer.msg("登录时请求失败，请重新尝试!",{icon : 5,anim : 6,time : 2000});
                            }
                          })
                        }else if (data == "fail"){
                          layer.msg("验证码不正确!",{icon : 5,anim : 6,time : 2000});
                        }
                      },
                      error:function (error) {
                      layer.msg("请求失败,请重新尝试!",{icon : 5,anim : 6,time : 2000});
                      }
                    })
                  }
              }
          }
      });






  
  /* form.on('submit(login)',function(){
		  $.ajax({
			  url:"${pageContext.request.contextPath}/Login/login",
			  type:"POST",
			  data:($("form#loginForm").serialize()),
			  success:function(data){
				  if(data=="success"){
					  window.location.href="${pageContext.request.contextPath}/Login/ToIndex"; 
				  }
				  
			  }
		  });
	  }); */
  
  <%--form.on("submit(login)",function(){--%>
	<%-- var $code = $("#verifyCode");--%>
	<%-- var value = $code.val();--%>
	<%-- --%>
	<%-- if(value=="" || value==null){--%>
	<%--	 layer.msg("验证码不能为空!",{icon : 5,anim : 6,time : 2000});--%>
	<%-- }else{--%>
	<%--	 $.ajax({//优先验证验证码--%>
	<%--		url:"${pageContext.request.contextPath}/Login/checkVerifyCode", --%>
	<%--		type:"POST",--%>
	<%--		data:{verifyCode:value},--%>
	<%--		success:function(data){//优先对验证码进行验证，如果通过则进行身份认证--%>
	<%--			if(data=="success"){//验证码正确--%>
	<%--			//身份验证--%>
	<%--			$.ajax({--%>
	<%--			  url:"${pageContext.request.contextPath}/Login/login",--%>
	<%--			  type:"POST",--%>
	<%--			  data:($("form#loginForm").serialize()),--%>
	<%--			  success:function(data){--%>
	<%--				  if(data=="success"){//由于服务端重定向对异步请求无效，所以需要使用bom 后期将该功能与node结合--%>
	<%--					  window.location.href="${pageContext.request.contextPath}/Login/ToIndex"; --%>
	<%--				  }else{--%>
	<%--					  layer.msg("用户名或密码错误!",{icon : 5,anim : 6,time : 2000});--%>
	<%--				  }--%>
	<%--				  --%>
	<%--			  },--%>
	<%--		  error:function(data){--%>
	<%--				layer.msg("请求失败,请重新尝试!",{icon : 5,anim : 6,time : 2000});--%>
	<%--			}--%>
	<%--		  });--%>
	<%--			}else{//验证码错误--%>
	<%--				layer.msg("验证码错误!",{icon : 5,anim : 6,time : 2000});--%>
	<%--			}--%>
	<%--		},--%>
	<%--		error:function(data){--%>
	<%--			layer.msg("请求失败,请重新尝试!",{icon : 5,anim : 6,time : 2000});--%>
	<%--		}--%>
	<%--		 --%>
	<%--		 --%>
	<%--	 });--%>
	<%-- }--%>
	<%--  --%>
  <%--});--%>
  
  //验证码
  /*$("#verifyCode").on('blur',function(){
	  var $codevalue = $("#verifyCode");
	  var value = $codevalue.val();
	  if(value!=null || value!=''){
		  $.ajax({
			url:"${pageContext.request.contextPath}/Login/checkVerifyCode", 
			type:"POST",
			data:{verifyCode:value},
			asyc:true,
			success:function(data){
				if(data=="fail"){
					layer.msg("验证码错误!",{icon : 5,anim : 6,time : 2000});
				}
			}	 
		  });
	  } 
	  
  })*/
 
	  });
  
  </script>
</body>

</html>