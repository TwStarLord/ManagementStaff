package com.tw.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class getVerifyCode {

	@RequestMapping("getVerifyCode")
	@ResponseBody
	public void VerifyCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		/*
		 * 1. 生成图片
		 * 2. 保存图片上的文本到session域中
		 * 3. 把图片响应给客户端
		 */
		VerifyCode vc = new VerifyCode();
		BufferedImage image = vc.getImage();
		request.getSession().setAttribute("session_vcode", vc.getText());//保存图片上的文本到session域，在logincheck中获取session中的值，从而进行验证码的判断
		
		VerifyCode.output(image, response.getOutputStream());
	}
}
