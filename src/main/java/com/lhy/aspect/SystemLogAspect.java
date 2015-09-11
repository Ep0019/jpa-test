package com.lhy.aspect;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lhy.service.RoleService;

/**
 * @author: 李慧勇
 * @description:
 * @mail:jdlglhy@163.com
 * @2015年7月6日
 * @version 1.0
 */
@Component
@Aspect
public class SystemLogAspect {
	
	private  static  final Logger logger = LoggerFactory.getLogger(SystemLogAspect. class);
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * @Title: controllerAspect
	 * @Description: controller切入点
	 * @return: void   
	 */
	@Pointcut("@annotation(com.lhy.aspect.SystemControllerLog)")
	public void controllerAspect(){}
	
	/**
	 * @Title: controllerAspect
	 * @Description: service切入点
	 * @return: void   
	 */
	@Pointcut("@annotation(com.lhy.aspect.SystemServiceLog)")
	public void serviceAspect(){}
	
	
	 /** 
     * 前置通知 用于拦截Controller层记录用户的操作 
	 * @param joinPoint 切点 
	 */  
    @Before("controllerAspect()")  
    public  void doBefore(JoinPoint joinPoint) {
    	System.out.println("---前置通知来了---");
    	try {
    	    System.out.println("请求方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));  
			System.out.println("方法描述:" + getControllerMethodDescription(joinPoint));
		} catch (Exception e) {
			e.printStackTrace();
		}  
    }
    /** 
     * 异常通知 用于拦截service层记录异常日志 
     * 
     * @param joinPoint 
     * @param e 
     */  
    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")  
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) { 
    	String params = "";  
	    if (joinPoint.getArgs() !=  null && joinPoint.getArgs().length > 0) {  
	         for ( int i = 0; i < joinPoint.getArgs().length; i++) {  
	        	 params += joinPoint.getArgs()[i] + ";";  
	         }  
	    }  
	    try {  
	        System.out.println("=====异常通知开始=====");  
	        System.out.println("异常代码:" + e.getClass().getName());  
	        System.out.println("异常信息:" + e.getMessage());  
	        System.out.println("异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));  
	        System.out.println("方法描述:" + getServiceMthodDescription(joinPoint));  
	        System.out.println("请求参数:" + params);  
	        /*==========记录本地异常日志==========*/
	       
	    }catch(Exception ex){
	    	ex.printStackTrace();
	    	logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage(), params);
	    }
    }

	/** 
     * 获取注解中对方法的描述信息 用于Controller层注解 
     * 
     * @param joinPoint 切点 
     * @return 方法描述 
     * @throws Exception 
     */  
     public  static String getControllerMethodDescription(JoinPoint joinPoint)  throws Exception {  
        String targetName = joinPoint.getTarget().getClass().getName();  
        String methodName = joinPoint.getSignature().getName();  
        Object[] arguments = joinPoint.getArgs();  
        Class targetClass = Class.forName(targetName);  
        Method[] methods = targetClass.getMethods();  
        String description = "";  
         for (Method method : methods) {  
             if (method.getName().equals(methodName)) {  
                Class[] clazzs = method.getParameterTypes();  
                 if (clazzs.length == arguments.length) {  
                    description = method.getAnnotation(SystemControllerLog. class).description();  
                     break;  
                }  
           }  
        }  
         return description;  
    }  
     /** 
      * 获取注解中对方法的描述信息 用于service层注解 
      * 
      * @param joinPoint 切点 
      * @return 方法描述 
      * @throws Exception 
      */  
      public  static String getServiceMthodDescription(JoinPoint joinPoint)  
              throws Exception {  
    	  System.out.println("-----------getServiceMthodDescription_---------------");
         String targetName = joinPoint.getTarget().getClass().getName();  
         String methodName = joinPoint.getSignature().getName();  
         Object[] arguments = joinPoint.getArgs();  
         Class targetClass = Class.forName(targetName);  
         Method[] methods = targetClass.getMethods();  
         String description = "";  
          for (Method method : methods) {  
              if (method.getName().equals(methodName)) {  
                 Class[] clazzs = method.getParameterTypes();  
                  if (clazzs.length == arguments.length) {  
                     description = method.getAnnotation(SystemServiceLog. class).description();  
                      break;  
                 }  
             }  
         }  
         return description;  
     }  

	
	
}
