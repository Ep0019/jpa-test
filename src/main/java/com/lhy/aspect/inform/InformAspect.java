package com.lhy.aspect.inform;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lhy.entity.Role;

/**
 * @author: 李慧勇
 * @description:aop前置通知，后置通知和后置通知返回
 * @mail:jdlglhy@163.com
 * @2015年7月6日
 * @version 1.0
 */
/*@Component
@Aspect
public class InformAspect {
	
	private  static  final Logger logger = LoggerFactory.getLogger(InformAspect. class);
	
	*//**
	 *定义切入点
	 *//*
	@Pointcut("execution(* com.lhy.service..*.*(..))")
	public void informAspect(){}
	
	 *//** 
     * 前置通知（会拦截带有参数String的方法）
	 * @param joinPoint 切点 
	 *//*  
    @Before("informAspect() && args(sql)")  
    public void doBefore(JoinPoint joinPoint,Object sql) {
    	System.out.println("---前置通知来了---"+sql);
    }

	 *//** 
     * 后置前置通知（会拦截带有参数String的方法）
	 * @param joinPoint 切点 
	 *//*  
    @After("informAspect() && args(role)")
    public  void doAfter(JoinPoint joinPoint,Role role) {
    	System.out.println("---后置通知来了---"+role.getName());
    }
    *//** 
     * 后置前置通知返回（会拦截返回值为String方法）
	 * @param joinPoint 切点 
    @AfterReturning(pointcut="informAspect()",returning="result")
    public void afterReturning(JoinPoint joinPoint,Object result){//Object根据需要指定类型
    	System.out.println("---后置通知返回来了---"+result);
    }*//*
    @AfterReturning(pointcut="informAspect()",returning="role")
    public void afterReturning(JoinPoint joinPoint,Role role){
    	System.out.println("---后置通知返回来了---"+role);
    }
    *//** 
     * 异常通知
     * @param joinPoint 
     * @param e 
     *//*  
    @AfterThrowing(pointcut = "informAspect()", throwing = "e")  
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) { 
    	logger.info("操作异常了", e);
    	System.out.println("系统异常");
    }
}*/
