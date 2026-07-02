package com.shopit.shop.Aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;
import java.time.Instant;

/**
 * Audit Aspect — records who did what and when for methods annotated with @Audited.
 *
 * In production, persist the AuditEntry to a database or send to an audit log sink.
 *
 * Usage:
 *   @Audited(action = "DELETE_PRODUCT")
 *   public void deleteProduct(Long id) { ... }
 */
@Aspect
@Component
@Order(3)
@Slf4j
public class AuditAspect {

    // ── Custom annotation ─────────────────────────────────────────────────────

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public @interface Audited {
        String action();
    }

    // ── Audit record ──────────────────────────────────────────────────────────

    public record AuditEntry(String user, String action, String method, Instant timestamp) {}

    // ── Advice ────────────────────────────────────────────────────────────────

    /**
     * Fires only on successful return (not on exception),
     * so failed operations are not recorded as completed actions.
     */
    @AfterReturning(
        pointcut = "@annotation(com.shopit.shop.Aspect.AuditAspect.Audited)"
    )
    public void audit(JoinPoint jp) {
        MethodSignature sig    = (MethodSignature) jp.getSignature();
        Audited         ann    = sig.getMethod().getAnnotation(Audited.class);
        String          user   = resolveCurrentUser();
        String          method = sig.getDeclaringType().getSimpleName() + "." + sig.getName();

        AuditEntry entry = new AuditEntry(user, ann.action(), method, Instant.now());

        // Replace with: auditRepository.save(entry) or eventBus.publish(entry)
        log.info("[AUDIT] user={} action={} method={} timestamp={}",
            entry.user(), entry.action(), entry.method(), entry.timestamp());
    }

    private String resolveCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null && auth.isAuthenticated()) ? auth.getName() : "anonymous";
    }
}
