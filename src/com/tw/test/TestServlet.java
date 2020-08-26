package com.tw.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.chainsaw.Main;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tw.service.StaffService;
import com.tw.service.impl.StaffServiceImpl;

@WebServlet("/smile")
public class TestServlet extends HttpServlet{

	private StaffService staffService;
	
	
	@Override
	public void init() throws ServletException {
//		 WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
//		 staffService = wac.getBean("staffService", StaffServiceImpl.class);
//		 System.out.println("取出来了"+staffService);
	}
	
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		System.out.println("经过控制器");
//		resp.sendRedirect("index.jsp");
//		
	}
}
