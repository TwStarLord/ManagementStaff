package com.tw.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 在这里有url权限验证，只拦截dispatch中的ToNotice mapping，如果没有对应的角色权限（所包含的url）是无法发布公告的
 * 根据数据库设计
 * @author tw
 *
 */
public class NoticeInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
//        System.out.println("评论拦截器!此处做评论敏感词汇拦截!");
//        System.out.println("获取到的评论内容为："+request.getParameter("comment"));
        String modifiedComment = request.getParameter("comment");

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
