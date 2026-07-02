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
 * Performance Aspect — measures execution time on methods annotated with @Timed.
 *
 * Usage:
 *   @Timed(threshold = 200)   // warns if method takes > 200 ms
 *   public Product findById(Long id) { ... }
 */
@Aspect
@Component
@Order(2)
@Slf4j
public class PerformanceAspect {

    // ── Custom annotation ─────────────────────────────────────────────────────

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface Timed {
        /** Warn if execution exceeds this many milliseconds. Default = 500. */
        long threshold() default 500;
    }

    // ── Advice ────────────────────────────────────────────────────────────────

    @Around("@annotation(com.shopit.shop.Aspect.PerformanceAspect.Timed)")
    public Object measureTime(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature sig  = (MethodSignature) pjp.getSignature();
        Timed           ann  = sig.getMethod().getAnnotation(Timed.class);
        long            threshold = ann.threshold();

        long start  = System.currentTimeMillis();
        Object result = pjp.proceed();
        long elapsed = System.currentTimeMillis() - start;

        String label = sig.getDeclaringType().getSimpleName() + "." + sig.getName();

        if (elapsed > threshold) {
            log.warn("⚠ SLOW METHOD [{}] took {}ms (threshold: {}ms)", label, elapsed, threshold);
        } else {
            log.debug("⏱ [{}] {}ms", label, elapsed);
        }

        return result;
    }
}
