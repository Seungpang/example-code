package me.serungpang.retryexample.support;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RetryOnOptimisticLockingFailure {

    int maxRetries() default 3;

    int delay() default 1000;
}
