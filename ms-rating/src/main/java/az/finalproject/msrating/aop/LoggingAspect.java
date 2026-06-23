package az.finalproject.msrating.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* az.finalproject.msrating.service.*.*(..))")
    public void ratingServiceMethods() {}

    @Around("ratingServiceMethods()")
    public Object logAndMeasure(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("RATING_LOG: Method [{}] started with args: {}", methodName, Arrays.toString(args));

        long startTime = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long timeTaken = System.currentTimeMillis() - startTime;
            log.info("RATING_LOG: Method [{}] finished in {} ms", methodName, timeTaken);
            return result;
        } catch (Throwable throwable) {
            log.error("RATING_LOG: Method [{}] failed: {}", methodName, throwable.getMessage());
            throw throwable;
        }
    }
}
