package com.shopit.shop.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Logging Aspect — logs method entry/exit, arguments, return values,
 * execution time, and exceptions for all classes in the service layer.
 *
 * Order(1) = runs outermost, so it wraps any inner aspects (e.g. @Transactional).
 */
@Aspect
@Component
@Order(1)
@Slf4j
public class LoggingAspect {

    /**
     * Pointcut: every public method in any class annotated with @Service or @Repository.
     */
    @Pointcut("within(@org.springframework.stereotype.Service *)" +
              " || within(@org.springframework.stereotype.Repository *)")
    public void springBeanPointcut() {}

    /**
     * Pointcut: every public method inside the application's base package.
     */
    @Pointcut("within(com.shopit.shop..*)")
    public void applicationPackagePointcut() {}

    /**
     * @Around advice combining both pointcuts.
     * Logs: method signature, arguments, return value, elapsed time.
     * On exception: logs at ERROR level then re-throws.
     */
    @Around("springBeanPointcut() && applicationPackagePointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String className  = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        if (log.isDebugEnabled()) {
            log.debug("→ {}.{}() args: {}",
                className, methodName, Arrays.toString(joinPoint.getArgs()));
        }

        long start = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            long elapsed = System.currentTimeMillis() - start;

            if (log.isDebugEnabled()) {
                log.debug("← {}.{}() returned: {} in {}ms",
                    className, methodName, result, elapsed);
            } else {
                log.info("← {}.{}() completed in {}ms", className, methodName, elapsed);
            }
            return result;

        } catch (Exception ex) {
            long elapsed = System.currentTimeMillis() - start;
            log.error("✗ {}.{}() threw {} after {}ms — message: {}",
                className, methodName,
                ex.getClass().getSimpleName(), elapsed, ex.getMessage());
            throw ex;
        }
    }

    /**
     * Separate @AfterThrowing to capture the full stack trace independently.
     * This fires even if @Around re-throws, so it's useful for dedicated error tracking.
     */
    @AfterThrowing(pointcut = "springBeanPointcut() && applicationPackagePointcut()",
                   throwing  = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        log.error("Exception in {}.{}() → {}",
            joinPoint.getSignature().getDeclaringTypeName(),
            joinPoint.getSignature().getName(),
            ex.getMessage(), ex);
    }
}
