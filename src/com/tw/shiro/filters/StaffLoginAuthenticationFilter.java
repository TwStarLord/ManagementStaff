package com.tw.shiro.filters;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.PrintWriter;

public class StaffLoginAuthenticationFilter extends FormAuthenticationFilter {

    /**
     *  此方法返回true的话则说明不继续访问，返回false则继续执行saveRequestAndRedirectToLogin方法? 是否正确
     *     此方法是请求未通过认证时执行的方法，按逻辑推理，请求未认证就跳转到loginurl在这里实现
     *
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {


        return super.onAccessDenied(request, response);
    }

//        @Override
//    protected boolean onAccessDenied(ServletRequest req, ServletResponse resp) throws Exception {
////        System.out.println("这里是验证码校验处");
//        HttpServletRequest request = (HttpServletRequest) req;
//        HttpServletResponse response = (HttpServletResponse) resp;
//        HttpSession session = request.getSession();
//        String vcode = (String) session.getAttribute("session_vcode");
//        String verifyCode = (String) request.getParameter("verifyCode");
//        System.out.println("验证码是否正确："+verifyCode.equalsIgnoreCase(vcode));
//
//        if(isAjaxRequest(request)){
//            //  验证码比较
//            if(verifyCode!=null && vcode!=null && !verifyCode.equalsIgnoreCase(vcode)){
//                //如果校验失败，将验证码错误失败信息，通过shiroLoginFailure设置到request中
//                request.setAttribute("shiroLoginFailure", "verifyCodeError");
//                //拒绝访问，不再校验账号和密码
//                return true;
//            }
//        }
//        return super.onAccessDenied(req, resp);
//
////        if(isLoginRequest(req,resp)){
////            if(isLoginSubmission(req,resp)){
////                if(verifyCode!=null && vcode!=null && !verifyCode.equalsIgnoreCase(vcode)){
////                    //如果校验失败，将验证码错误失败信息，通过shiroLoginFailure设置到request中
////                    request.setAttribute("shiroLoginFailure", "verifyCodeError");
////                    //拒绝访问，不再校验账号和密码
////                    return true;
////                }
////            }
////        }else {
////            if(isAjaxRequest(request)){
////                response.getWriter().write(JSON.toJSONString(ResultBuilder.genExpResult(new AppBizException(AppExcCodesEnum.SESSION_TIMEOUT))));
////            }else{
////                this.saveRequestAndRedirectToLogin(request, response);
////            }
////            return false;
////            return super.onAccessDenied(req, resp);
////        }
//
//    }

    /**
     * 判断该请求是否为异步请求
     * @param req
     * @return
     */
    private static boolean isAjaxRequest(ServletRequest req){
        String header = ((HttpServletRequest) req).getHeader("X-Requested-With");
        if("XMLHttpRequest".equalsIgnoreCase(header)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    /**
     * 登录成功时
     * @param token
     * @param subject
     * @param req
     * @param resp
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest req, ServletResponse resp) throws Exception {
//        if (!StringUtils.isEmpty(getSuccessUrl())) {
//            // getSession(false)：如果当前session为null,则返回null,而不是创建一个新的session
//            Session session = subject.getSession(false);
//            if (session != null) {
//                session.removeAttribute("shiroSavedRequest");
//            }
//        }

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        if(!isAjaxRequest(request)){
            issueSuccessRedirect(request,response);
        }else{
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.println("{\"data\":\"success\"}");
            out.flush();
            out.close();
        }

        return false;
    }

    /**
     * 这里不仅是登录时检测，
     * 直接在此onLoginFailure方法中从request域中获取属性为shiroFailure属性，
     * 先判空，在判断错误信息是否为验证码错误信息，如果是的话返回json字符串提示验证码错误
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        if (!"XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request)
                .getHeader("X-Requested-With"))) {// 不是ajax请求
            setFailureAttribute(request, e);
            return true;
        }
        try {
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            String message = e.getClass().getSimpleName();
            if ("IncorrectCredentialsException".equals(message)) {
                out.println("{\"data\":\"pwdError\"}");
            } else if ("UnknownAccountException".equals(message)) {
                out.println("{\"data\":\"accountError\"}");
            } else {
                out.println("{\"data\":\"otherError\"}");
            }
            out.flush();
            out.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return false;
    }
}
