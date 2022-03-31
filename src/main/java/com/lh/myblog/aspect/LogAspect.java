package com.lh.myblog.aspect;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 利用spring的切面查看访问信息，输出到日志
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("execution(* com.lh.myblog.controller.*.*(..))")
    public void log(){}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        RequestLog requestLog = new RequestLog(url, ip, classMethod, args);

        log.info("Request : {}",requestLog);
    }

    @After("log()")
    public void doAfter(){}

    @AfterReturning(returning = "result",pointcut = "log()")
    public void doAfterReturn(Object result){
        log.info("Result : {}" , result);
    }

    //封装请求的各项属性的内部类
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    private class RequestLog{

        private String url;
        private String ip;
        private String classMethod;
        private Object[] args;
    }
}
