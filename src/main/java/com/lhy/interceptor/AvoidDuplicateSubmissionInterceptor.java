package com.lhy.interceptor;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lhy.annotation.AvoidDouSubmit;
import com.lhy.entity.Role;
import com.lhy.service.RoleService;

/**
 * @author: 李慧勇
 * @description:
 * @mail:jdlglhy@163.com
 * @2015年7月15日
 * @version 1.0
 */
public class AvoidDuplicateSubmissionInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	private RoleService roleService;
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
 
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
 
            AvoidDouSubmit annotation = method.getAnnotation(AvoidDouSubmit.class);
            if (annotation != null) {
                boolean needSaveSession = annotation.needSaveToken();
                if (needSaveSession) {
                	if(isRepeatSubmit(request)){
                		System.out.println("please don't repeat submit,url:"
                                + request.getServletPath() + "]");
                		response.setCharacterEncoding("UTF-8");  
           				response.setContentType("application/json; charset=utf-8");  
           				PrintWriter out = response.getWriter();
           				out.write("{\"code\":\"4\",\"msg\":\"请不要重复提交表单\"}");
           				out.close();
                        return false;
                	}
                	 request.getSession(false).removeAttribute("token");
                   /* request.getSession(false).setAttribute("token", UUID.randomUUID().toString());*/
                	
                }
               /* boolean needRemoveSession = annotation.needRemoveToken();
                if (needRemoveSession) {
                    if (isRepeatSubmit(request)) {
                    	System.out.println("please don't repeat submit,url:"
                                + request.getServletPath() + "]");
                        return false;
                    }
                    request.getSession(false).removeAttribute("token");
                }*/
            }
        return true;
    }
 
    private boolean isRepeatSubmit(HttpServletRequest request) {
        String serverToken =(String)request.getSession(false).getAttribute("token");
        if (serverToken == null) {
            return true;
        }
        String clinetToken = request.getParameter("token");
        if (clinetToken == null) {
            return true;
        }
        if (!serverToken.equals(clinetToken)) {
            return true;
        }
        return false;
    }


}
