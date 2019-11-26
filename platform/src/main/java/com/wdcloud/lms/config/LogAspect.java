package com.wdcloud.lms.config;


import com.wdcloud.lms.WebContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
@Slf4j
@Component
@Aspect
public class LogAspect {


	/**
     * 定义一个切入点.
     * 解释下：
     *
     * ~ 第一个 * 代表任意修饰符及任意返回值.
     * ~ 第二个 * 定义在web包或者子包
     * ~ 第三个 * 任意方法
     * ~ .. 匹配任意数量的参数.
     */
     @Pointcut("execution(* com.wdcloud.server.frame.api.*Controller.*(..)) " +
			 "&& !execution(* com.wdcloud.server.frame.api.BaseController.*(..))" +
			 "||execution(* com.wdcloud.lms.api.*Controller.*(..))")
     public void logPointcut(){}

     @org.aspectj.lang.annotation.Around("logPointcut()")
     public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable{
 		 long start = System.currentTimeMillis();
		 HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		 String in = "post".equalsIgnoreCase(request.getMethod())?((BodyReaderRequestWrapper)request).getBody(): request.getQueryString();
		 log.info("【{}:in={}】【入参】:{}【loginUserId={}】",start, request.getRequestURI() , in, WebContext.getUserId());
		 Object result =joinPoint.proceed();
		 long exeTime = System.currentTimeMillis() - start;
		 log.info("【{}:out={}】{}:{}ms!" ,start, request.getRequestURI() ,exeTime > 1000 ? "长耗时" : "短耗时", exeTime);
		 return result;
	 }

}