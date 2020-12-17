package com.geekbrains.shop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* com.geekbrains.shop.controller..*.*(..))")
    private void anyMethod() {
    }

    @Before("anyMethod()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("Log before ->" + joinPoint);
    }


}

