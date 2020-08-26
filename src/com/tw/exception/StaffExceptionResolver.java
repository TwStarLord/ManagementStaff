package com.tw.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 全局异常处理
 */
public class StaffExceptionResolver implements HandlerExceptionResolver {

    //前端控制器DispatcherServlet在进行HandlerMapping、调用HandlerAdapter执行Handler过程中，如果遇到异常就会执行此方法
    //handler最终要执行的Handler，它的真实身份是HandlerMethod
    //Exception ex就是接收到异常信息
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Exception ex) {
        //输出异常
        ex.printStackTrace();


        //统一异常处理代码
        //针对系统自定义的CustomException异常，就可以直接从异常类中获取异常信息，将异常处理在错误页面展示
        //异常信息
        String message = "";
        StaffException staffException = null;
        if(ex instanceof StaffException){
            ex = (StaffException) ex;
        }else if(ex instanceof UnauthorizedException){
            ex = new StaffException("unauthorization");
        }else{
            staffException = new StaffException("otherError");
        }

        //错误信息
        message = ex.getMessage();
//        System.out.println("message的值为："+message);
//        System.out.println("message的类型为："+message.getClass().toString());
//
//        System.out.println("测试错误是否为verifyCodeError："+("verifyCodeError".equalsIgnoreCase(message)));

        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            out = response.getWriter();
            if ("unauthorization".equals(message)) {  //未授权，弹窗提示信息
                out.println("{\"data\":\"unauthorized\"}");
            }else if ("accountError".equals(message)) {
                out.println("{\"data\":\"accountError\"}");
            } else if ("pwdError".equals(message)) {
                out.println("{\"data\":\"pwdError\"}");
            } else {
                out.println("{\"data\":\"otherError\"}");
            }
            out.flush();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }finally {
            if(out != null){
                out.close();
            }
        }

        return new ModelAndView();
    }

}
