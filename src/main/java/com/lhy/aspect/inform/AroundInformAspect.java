package com.lhy.aspect.inform;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author: 李慧勇
 * @description:aop环绕通知
 * @mail:jdlglhy@163.com
 * @2015年7月6日
 * @version 1.0
 */
/*@Component
@Aspect
public class AroundInformAspect {
	
	
	*//**
	 *定义切入点
	 *//*
	@Pointcut("execution(* com.lhy.service..*.*(..))")
	public void aroundInformAspect(){}
	
	 *//** 
     * 前置通知（会拦截带有参数String的方法）
	 * @param joinPoint 切点 
	 *//*  
    @Around("aroundInformAspect()")  
    public void doAround(ProceedingJoinPoint pjp) {
    	  System.out.println("Around 前:");
    	  System.out.println("getSignature():"+pjp.getSignature());
    	  System.out.println("getTarget():"+pjp.getTarget());
    	  Object[] args=pjp.getArgs();
    	  if(args != null && args.length==2){
    		  System.out.println(args[0]+"---"+args[1]);
    	  }
    	  Object[] newArgs={"around改变参数",222l};
          try {
        	  pjp.proceed(newArgs);
          } catch (Throwable e) {
              e.printStackTrace();
          }
          System.out.println("Around 后:");

    }

	
}*/
