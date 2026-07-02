package com.shopit.shop.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Retry Aspect — automatically retries a method on failure.
 *
 * Usage:
 *   @Retryable(maxAttempts = 3, delay = 500, retryOn = {IOException.class})
 *   public String callExternalApi() { ... }
 *
 * Note: For production use Spring Retry (@EnableRetry + @Retryable from
 * spring-retry library) which also supports exponential backoff and @Recover.
 * This aspect shows the raw AOP implementation for learning purposes.
 */
@Aspect
@Component
@Order(4)
@Slf4j
public class RetryAspect {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface Retryable {
        int maxAttempts() default 3;
        long delay() default 1000;  // ms between attempts
        Class<? extends Throwable>[] retryOn() default {Exception.class};
    }

    @Around("@annotation(com.shopit.shop.Aspect.RetryAspect.Retryable)")
    public Object retry(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature sig      = (MethodSignature) pjp.getSignature();
        Retryable       ann      = sig.getMethod().getAnnotation(Retryable.class);
        int             maxTries = ann.maxAttempts();
        long            delay    = ann.delay();
        String          label    = sig.getDeclaringType().getSimpleName() + "." + sig.getName();

        Throwable lastEx = null;
        for (int attempt = 1; attempt <= maxTries; attempt++) {
            try {
                if (attempt > 1) {
                    log.info("↻ Retry attempt {}/{} for {}", attempt, maxTries, label);
                }
                return pjp.proceed();
            } catch (Throwable ex) {
                if (!isRetryable(ex, ann.retryOn())) {
                    log.warn("✗ Non-retryable exception in {}: {}", label, ex.getMessage());
                    throw ex;
                }
                lastEx = ex;
                log.warn("✗ Attempt {}/{} failed for {}: {}", attempt, maxTries, label, ex.getMessage());

                if (attempt < maxTries) {
                    Thread.sleep(delay);
                }
            }
        }
        log.error("✗ All {} attempts exhausted for {}", maxTries, label);
        throw lastEx;
    }

    private boolean isRetryable(Throwable ex, Class<? extends Throwable>[] retryOn) {
        for (Class<? extends Throwable> type : retryOn) {
            if (type.isInstance(ex)) return true;
        }
        return false;
    }
}
