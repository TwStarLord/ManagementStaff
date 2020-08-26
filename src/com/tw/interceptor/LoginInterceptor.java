package com.tw.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登录拦截，从cookie中检测用户信息，如果没有相关信息的话不能登陆
 * 后期需要加上权限验证（结合shiro框架）
 * 单点登录？cookie操作  设置有效时间
 * 
 * @author tw
 *
 */
public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		//获取请求uri，如果为login放行，如果为其他需要判断有没有登录信息，否则不能访问，后期加上权限验证
		String uri = request.getRequestURI();
		//获取项目路径
		String context = request.getContextPath();
		Object o = request.getSession().getAttribute("staffinfo");
		//测试时发现css、js、等文件都会被拦截拦截，说明在spring.mvc中配置的静态资源放行还是会在这里被拦下来
		/*
		 * if((context+"/login").equals(uri)||(context+"/login.jsp").equals(uri)||(
		 * context+"/getVerifyCode").equals(uri)||uri.startsWith(context+"/layui")||uri.
		 * startsWith(context+"resources")) { System.out.println("我的请求为可以进到系统内部");
		 * return true; }else {
		 */
		System.out.println("进入拦截器");
		if(o!=null) {//登录成功之后会将登录信息储存到session会话中，只要不为空即可访问系统，但是一个浏览器只能支持一个同类型的session，如果两个用户同时登录，会造成后登录者将前者的session会话覆盖，导致前者失去访问权限
			return true;
		}else {
			System.out.println("我的请求不是login.jsp，需要跳转到login.jsp");
			response.sendRedirect(context+"/login.jsp");
			return false;
		}
		//}
		
	}
	
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
