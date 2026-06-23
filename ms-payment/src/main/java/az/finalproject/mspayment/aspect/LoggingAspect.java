package az.finalproject.mspayment.aspect;

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

    @Pointcut("execution(* az.finalproject.mspayment.service.*.*(..))")
    public void paymentServiceMethods() {}

    @Around("paymentServiceMethods()")
    public Object logAndMeasure(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("PAYMENT_LOG: Method [{}] started with args: {}", methodName, Arrays.toString(args));

        long startTime = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            log.info("PAYMENT_LOG: Method [{}] finished in {} ms", methodName, executionTime);
            return result;
        } catch (Throwable throwable) {
            log.error("PAYMENT_LOG: Method [{}] failed. Error: {}", methodName, throwable.getMessage());
            throw throwable;
        }
    }
}
