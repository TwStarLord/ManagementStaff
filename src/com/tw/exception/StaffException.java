package com.tw.exception;

/**
 * 用户登录或者注册时跑出异常信息并接收
 * @author tw
 *
 */
public class StaffException extends Exception {

	public StaffException() {
		
	}

	public StaffException(String message) {
		super(message);
	}

//	public StaffException(Throwable cause) {
//		super(cause);
//		// TODO Auto-generated constructor stub
//	}
//
//	public StaffException(String message, Throwable cause) {
//		super(message, cause);
//		// TODO Auto-generated constructor stub
//	}
//
//	public StaffException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
//		super(message, cause, enableSuppression, writableStackTrace);
//		// TODO Auto-generated constructor stub
//	}

}
