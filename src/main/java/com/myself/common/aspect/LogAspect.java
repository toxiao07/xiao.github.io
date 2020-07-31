package com.myself.common.aspect;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Properties;

@Aspect
@Component
@Slf4j
public class LogAspect {

    private static Logger logger = Logger.getLogger(LogAspect.class);



    @Pointcut("execution(* com.myself.modules.*.Controller..*(..))")
    public void webLog() {
    }

    @Around("webLog()")
    //@After("webLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("=====================================Method  Start====================================");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long end = System.currentTimeMillis();
            log.info("入参:" + Arrays.toString(joinPoint.getArgs()));
            log.info("请求地址:" + request.getRequestURI());
            log.info("用户IP:" + request.getRemoteAddr());
            String controller = joinPoint.getSignature().getDeclaringTypeName();
            log.info("controller: " + controller);
            log.info("方法 :" + joinPoint.getSignature().getName());
            log.info("执行时间:" + (end - start) + " ms!");
            log.info("出参:{}", JSON.toJSONString(result));
            logger.info("result" + JSON.toJSONString(result));
            log.info("=====================================Method  End====================================");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        //格式化sql语句
        Properties properties = new Properties();
        properties.setProperty("format", "true");
        performanceInterceptor.setProperties(properties);
        return performanceInterceptor;
    }
}
