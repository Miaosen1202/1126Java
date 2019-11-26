package com.wdcloud.lms.config;

import com.alibaba.fastjson.JSON;
import com.wdcloud.base.exception.BaseException;
import com.wdcloud.lms.WebContext;
import com.wdcloud.redis.IRedisService;
import com.wdcloud.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 防重复提交aop
 * @author wangff
 */
@Slf4j
@Aspect
@Component
public class AccessLimitAspect {

    @Autowired
    private IRedisService redisService;
    /**
     * @param point
     */
    @Around("@annotation(com.wdcloud.lms.config.AccessLimit)")
    public Object around(ProceedingJoinPoint point) throws Throwable {

        HttpServletRequest request  = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String requestBody = "post".equalsIgnoreCase(request.getMethod())?((BodyReaderRequestWrapper)request).getBody(): request.getQueryString();
        String param=JSON.toJSONString(point.getArgs());

        String ip = IpUtils.getIpAdrress(request);
        //获取注解
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        //目标类、方法
        String className = method.getDeclaringClass().getName();
        String name = method.getName();
        String ipKey = String.format("%s#%s#%s#%s",className,name,requestBody,param);
        int hashCode = Math.abs(ipKey.hashCode());
        String redisKey = String.format("%d_%s_%d", WebContext.getUserId(),ip,hashCode);
        log.info("ipKey={},redisKey={}",ipKey,redisKey);
        AccessLimit accessLimit =  method.getAnnotation(AccessLimit.class);
        long timeout = accessLimit.timeScope();
        String value = redisService.get(redisKey);
        if (StringUtils.isNotBlank(value)){
            throw new BaseException("calling_too_frequently");
        }
        redisService.set(redisKey, "1");
        redisService.expire(redisKey, timeout,TimeUnit.SECONDS );
        //执行方法
        Object object = point.proceed();
        return object;
    }

}