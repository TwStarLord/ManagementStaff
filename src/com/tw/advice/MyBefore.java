package com.tw.advice;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.aop.MethodBeforeAdvice;

import com.tw.pojo.Staff;

public class MyBefore implements MethodBeforeAdvice{

	@Override
	public void before(Method arg0, Object[] arg1, Object arg2) throws Throwable {
		Staff users = (Staff) arg1[0];
		System.out.println(users);
		Logger logger = Logger.getLogger(MyBefore.class);
		logger.info(users.getName()+"在"+new Date().toLocaleString()+"进行登录");
		
	}

}
