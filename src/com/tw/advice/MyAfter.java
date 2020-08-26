package com.tw.advice;

import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.aop.AfterReturningAdvice;

import com.tw.pojo.Staff;

public class MyAfter implements AfterReturningAdvice{

	@Override
	public void afterReturning(Object arg0, Method arg1, Object[] arg2, Object arg3) throws Throwable {
		Logger logger = Logger.getLogger(MyAfter.class);
		Staff users = (Staff) arg2[0];
		//Object arg0这个返回值就是serviceimpl中的login方法的返回值，如果不为空，说明登录信息正确，记录登录成功
		if(arg0!=null){
			logger.info(users.getName()+"登录成功!");
		}else{
			logger.info(users.getName()+"登录失败!");
		}
		
	}

}
