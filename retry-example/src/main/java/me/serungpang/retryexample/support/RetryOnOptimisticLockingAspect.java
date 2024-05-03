package me.serungpang.retryexample.support;

import jakarta.persistence.OptimisticLockException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.StaleObjectStateException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Order(Ordered.LOWEST_PRECEDENCE - 1)
@Component
public class RetryOnOptimisticLockingAspect {

    @Around("@annotation(RetryOnOptimisticLockingFailure)")
    public Object doConcurrentOperation(final ProceedingJoinPoint pjp) throws Throwable {
        int numAttempts = 0;
        final RetryOnOptimisticLockingFailure annotation = ((MethodSignature) pjp.getSignature()).getMethod().getAnnotation(RetryOnOptimisticLockingFailure.class);
        final int maxRetries = annotation.maxRetries();
        final int delay = annotation.delay();
        do {
            numAttempts++;
            try {
                return pjp.proceed();
            } catch (final OptimisticLockException | StaleObjectStateException |
                           ObjectOptimisticLockingFailureException oe) {
                log.error("RetryOnOptimisticLockingFailure: retrying operation {} due to optimistic locking error, attempt {}", pjp.getSignature().getName(), numAttempts);
                if (numAttempts > maxRetries) {
                    log.error("RetryOnOptimisticLockingFailure: max retries exceeded");
                    throw oe;
                }
                Thread.sleep(delay); // 재시도 전 지연
            }
        } while (numAttempts <= maxRetries);

        // 이 부분은 실행되지 않는다.
        return null;
    }
}
