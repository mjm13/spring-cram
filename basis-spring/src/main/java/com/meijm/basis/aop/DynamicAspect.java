package com.meijm.basis.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 动态AOP切面
 * 启动代理切所有项目bean，通过map存储切点表达式
 * 符合条件的bean经过日志处理
 */
@Slf4j
@Aspect
@Component
public class DynamicAspect {

    // 存储动态添加的切点表达式
    private final ConcurrentHashMap<String, String> pointcutExpressions = new ConcurrentHashMap<>();

    // 动态添加切点
    public void addPointcut(String id, String expression) {
        pointcutExpressions.put(id, expression);
    }

    // 动态移除切点
    public void removePointcut(String id) {
        pointcutExpressions.remove(id);
    }

    // 使用@Pointcut定义可重用的切点
    @Pointcut("execution(* com.meijm.basis..*(..))")
    public void anyMethod() {}

    // 动态判断是否需要织入通知
    @Around("anyMethod()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        String methodName = point.getSignature().toLongString();

        // 检查当前方法是否匹配任何已注册的切点表达式
        boolean shouldIntercept = pointcutExpressions.values().stream()
                .anyMatch(expression -> {
                    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
                    pointcut.setExpression(expression);
                    return pointcut.matches(point.getTarget().getClass()) &&
                            pointcut.matches(((MethodSignature)point.getSignature()).getMethod(),
                                    point.getTarget().getClass());
                });

        if (shouldIntercept) {
            // 执行通知逻辑
            log.info("Before method: " + methodName);
            Object result = point.proceed();
            log.info("After method: " + methodName);
            return result;
        }

        return point.proceed();
    }
}
