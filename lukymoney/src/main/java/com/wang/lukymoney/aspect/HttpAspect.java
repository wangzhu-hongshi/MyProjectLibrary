package com.wang.lukymoney.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * AOP处理
 */
@Aspect
@Component
public class HttpAspect {
    //打印日志
    private final static Logger logger= LoggerFactory.getLogger(HttpAspect.class);
    //aop表达式
    @Pointcut("execution(public * com.wang.lukymoney.controller.LuckymoneyController.*(..))")
    public void log(){
    }
    //在执行被拦截的方法前执行
    @Before("log()")
    public void doBefore(){
        logger.info("11111111111111");
    }
    //之后执行
    @After("log()")
    public void doAfter(){
        logger.info("2222222222222");
    }
}
