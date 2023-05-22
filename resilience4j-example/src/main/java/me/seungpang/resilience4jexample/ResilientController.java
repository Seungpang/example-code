package me.seungpang.resilience4jexample;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public String fallbackAfterRetry(Exception ex) {
        return "모든 재시도 요청 실패";
    }
}
