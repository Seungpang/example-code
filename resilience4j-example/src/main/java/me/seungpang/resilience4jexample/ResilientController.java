package me.seungpang.resilience4jexample;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/")
public class ResilientController {

    private final ExternalApiCaller externalApiCaller;

    public ResilientController(ExternalApiCaller externalApiCaller) {
        this.externalApiCaller = externalApiCaller;
    }

    @GetMapping("/circuit-breaker")
    @CircuitBreaker(name = "CircuitBreakerService")
    public String circuitBreakerApi() {
        return externalApiCaller.callApi();
    }

    @GetMapping("/retry")
    @Retry(name = "retryApi", fallbackMethod = "fallbackAfterRetry")
    public String retryApi() {
        return externalApiCaller.callApi();
    }

    private String fallbackAfterRetry(Exception ex) {
        return "모든 재시도 요청 실패";
    }

    @GetMapping("/time-limiter")
    @TimeLimiter(name = "timeLimiterApi")
    public CompletableFuture<String> timeLimiterApi() {
        return CompletableFuture.supplyAsync(externalApiCaller::callApiWithDelay);
    }

    @GetMapping("/bulkhead")
    @Bulkhead(name = "bulkheadApi")
    public String bulkheadApi() {
        return externalApiCaller.callApi();
    }

    @GetMapping("/rate-limiter")
    @RateLimiter(name = "rateLimiterApi")
    public String rateLimitApi() {
        return externalApiCaller.callApi();
    }
}
