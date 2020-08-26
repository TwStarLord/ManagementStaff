package com.tw.listener;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.tw.pojo.Staff;

public class SessionListener implements HttpSessionAttributeListener{

	//private ServletContext context;
	/**
	 * set时触发
	 */
	@Override
	public void attributeAdded(HttpSessionBindingEvent event) {
		System.out.println("开始赋值");
		//获取事件对应的session
		HttpSession session = event.getSession();
		//
		ServletContext context = event.getSession().getServletContext();
		HashSet<HttpSession> sessionSet = (HashSet<HttpSession>) context.getAttribute("sessionSet");
		if(sessionSet==null) {
			sessionSet = new HashSet<HttpSession>();
		}
		if(session.getAttribute("session_vcode")!=null && session.getAttribute("staffinfo")==null) {//说明该session赋值行为是由验证码操作导致，不加用户
			System.out.println("我进的是验证码赋值");
			return;
		}else if(session.getAttribute("staffinfo")!=null){//用户信息
			for (HttpSession httpSession : sessionSet) {
				if((((Staff)httpSession.getAttribute("staffinfo")).getAccount()).equals(((Staff)session.getAttribute("staffinfo")).getAccount())) {//该session信息已经存在，强制清除
					System.out.println("我进的是用户信息赋值内部");
					sessionSet.remove(httpSession);
				}
			}
			//将用户信息添加
			System.out.println("我进的是用户信息赋值外部");
			sessionSet.add(session);
			context.setAttribute("sessionSet", sessionSet);
			context.setAttribute("onlineCount", sessionSet.size());
		}	
		else {
			System.out.println("我进的啥也不是赋值");
			return;}
	}
	
    /**
     * remove时触发
     */
	@Override
	public void attributeRemoved(HttpSessionBindingEvent event) {
		System.out.println("开始移除");
		HttpSession session = event.getSession();
		if(session.getAttribute("session_vcode")!=null) {
			System.out.println("我进的是验证码移除");
			return;
		}else if(session.getAttribute("staffinfo")!=null){
		ServletContext context = session.getServletContext();
		//移除session
		HashSet<HttpSession> sessionSet = (HashSet<HttpSession>) context.getAttribute("sessionSet");
		sessionSet.remove(session);
		Integer count = Integer.valueOf(((int)context.getAttribute("onlineCount")-1));
		context.setAttribute("onlineCount", count);
		}
		else {
			System.out.println("我进的是啥也不是移除");
			return;
		}
	}
	@Override
	public void attributeReplaced(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	
}
