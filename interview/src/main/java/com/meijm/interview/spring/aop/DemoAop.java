package com.meijm.interview.spring.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class DemoAop {

    @Before("execution(* com.meijm.interview.spring.Demo.division())")
    public void bfefore(JoinPoint joinPoint) throws Throwable{

    }
    @After("execution(* com.meijm.interview.spring.Demo.division())")
    public void after(JoinPoint joinPoint) throws Throwable{

    }

    @Around("execution(* com.meijm.interview.spring.Demo.division())")
    public void around(ProceedingJoinPoint pjp) throws Throwable{

    }

    @AfterReturning("execution(* com.meijm.interview.spring.Demo.division())")
    public void afterReturning(JoinPoint joinPoint) throws Throwable{

    }

    @AfterThrowing("execution(* com.meijm.interview.spring.Demo.division())")
    public void afterThrowing(JoinPoint joinPoint) throws Throwable{

    }
}
