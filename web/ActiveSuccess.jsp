﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>激活成功界面</title>
<link href="${pageContext.request.contextPath}/resources/css/404.css" rel="stylesheet" type="text/css" />
<script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
<script type="text/javascript">
	$(function() {
		var h = $(window).height();
		$('body').height(h);
		$('.mianBox').height(h);
		centerWindow(".tipInfo");
	});

	//2.将盒子方法放入这个方，方便法统一调用
	function centerWindow(a) {
		center(a);
		//自适应窗口
		$(window).bind('scroll resize',
				function() {
					center(a);
				});
	}

	//1.居中方法，传入需要剧中的标签
	function center(a) {
		var wWidth = $(window).width();
		var wHeight = $(window).height();
		var boxWidth = $(a).width();
		var boxHeight = $(a).height();
		var scrollTop = $(window).scrollTop();
		var scrollLeft = $(window).scrollLeft();
		var top = scrollTop + (wHeight - boxHeight) / 2;
		var left = scrollLeft + (wWidth - boxWidth) / 2;
		$(a).css({
			"top": top,
			"left": left
		});
	}
</script>
</head>
<body>
<div class="mianBox">
	<img src="${pageContext.request.contextPath}/resources/images/yun0.png" alt="" class="yun yun0" />
	<img src="${pageContext.request.contextPath}/resources/images/yun1.png" alt="" class="yun yun1" />
	<img src="${pageContext.request.contextPath}/resources/images/yun2.png" alt="" class="yun yun2" />
	<img src="${pageContext.request.contextPath}/resources/images/bird.png" alt="" class="bird" />
	<img src="${pageContext.request.contextPath}/resources/images/san.png" alt="" class="san" />
	<div class="tipInfo">
		<div class="in">
			<div class="textThis">
				<h2>激活成功!</h2>
<%--				<p><span>页面自动<a id="href" href="">跳转</a></span><span>等待<b id="wait">3</b>秒</span></p>--%>
<%--				<script type="text/javascript">                            (function() {--%>
<%--						var wait = document.getElementById('wait'), href = document.getElementById('href').href;--%>
<%--						var interval = setInterval(function() {--%>
<%--							var time = --wait.innerHTML;--%>
<%--							if (time <= 0) {--%>
<%--								--%>
<%--								clearInterval(interval);--%>
<%--								//location.href = href;--%>
<%--								alert("开始跳转提示");--%>
<%--							}--%>
<%--							;--%>
<%--						}, 1000);--%>
<%--					})();--%>
<%--				</script>--%>
			</div>
		</div>
	</div>
</div>

</body>
</html>
