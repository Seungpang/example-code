package me.seungpang.resilience4jexample;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ResilientControllerTest {

    @RegisterExtension
    static WireMockExtension EXTERNAL_SERVICE = WireMockExtension.newInstance()
            .options(WireMockConfiguration.wireMockConfig()
                    .port(9090))
            .build();

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testCircuitBreaker() {
        EXTERNAL_SERVICE.stubFor(WireMock.get("/api/external")
                .willReturn(serverError()));

        IntStream.rangeClosed(1, 5)
                .forEach(i -> {
                    ResponseEntity<String> response = restTemplate.getForEntity("/api/circuit-breaker", String.class);
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
                });

        IntStream.rangeClosed(1, 5)
                .forEach(i -> {
                    ResponseEntity<String> response = restTemplate.getForEntity("/api/circuit-breaker", String.class);
                    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
                });

        EXTERNAL_SERVICE.verify(5, getRequestedFor(urlEqualTo("/api/external")));
    }

    @Test
    void testRetry() {
        EXTERNAL_SERVICE.stubFor(WireMock.get("/api/external")
                .willReturn(ok()));
        ResponseEntity<String> response1 = restTemplate.getForEntity("/api/retry", String.class);
        EXTERNAL_SERVICE.verify(1, getRequestedFor(urlEqualTo("/api/external")));

        EXTERNAL_SERVICE.resetRequests();

        EXTERNAL_SERVICE.stubFor(WireMock.get("/api/external")
                .willReturn(serverError()));
        ResponseEntity<String> response2 = restTemplate.getForEntity("/api/retry", String.class);
        assertThat(response2.getBody()).isEqualTo("모든 재시도 요청 실패");
        EXTERNAL_SERVICE.verify(3, getRequestedFor(urlEqualTo("/api/external")));
    }

    @Test
    void testTimeLimiter() {
        EXTERNAL_SERVICE.stubFor(WireMock.get("/api/external").willReturn(ok()));
        ResponseEntity<String> response = restTemplate.getForEntity("/api/time-limiter", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.REQUEST_TIMEOUT);
        EXTERNAL_SERVICE.verify(1, getRequestedFor(urlEqualTo("/api/external")));
    }

    @Test
    void testBulkhead() {
        EXTERNAL_SERVICE.stubFor(WireMock.get("/api/external")
                .willReturn(ok()));
        Map<Integer, Integer> responseStatusCount = new ConcurrentHashMap<>();

        IntStream.rangeClosed(1,5)
                .parallel()
                .forEach(i -> {
                    ResponseEntity<String> response = restTemplate.getForEntity("/api/bulkhead", String.class);
                    int statusCode = response.getStatusCodeValue();
                    responseStatusCount.put(statusCode, responseStatusCount.getOrDefault(statusCode, 0) + 1);
                });

        assertThat(responseStatusCount.keySet().size()).isEqualTo(2);
        assertThat(responseStatusCount.containsKey(BANDWIDTH_LIMIT_EXCEEDED.value())).isTrue();
        assertThat(responseStatusCount.containsKey(OK.value())).isTrue();
        EXTERNAL_SERVICE.verify(3, getRequestedFor(urlEqualTo("/api/external")));
    }

    @Test
    public void testRatelimiter() {
        EXTERNAL_SERVICE.stubFor(WireMock.get("/api/external")
                .willReturn(ok()));
        Map<Integer, Integer> responseStatusCount = new ConcurrentHashMap<>();

        IntStream.rangeClosed(1, 50)
                .parallel()
                .forEach(i -> {
                    ResponseEntity<String> response = restTemplate.getForEntity("/api/rate-limiter", String.class);
                    int statusCode = response.getStatusCodeValue();
                    responseStatusCount.put(statusCode, responseStatusCount.getOrDefault(statusCode, 0) + 1);
                });

        assertThat(responseStatusCount.keySet().size()).isEqualTo(2);
        assertThat(responseStatusCount.containsKey(TOO_MANY_REQUESTS.value())).isTrue();
        assertThat(responseStatusCount.containsKey(OK.value())).isTrue();
        EXTERNAL_SERVICE.verify(5, getRequestedFor(urlEqualTo("/api/external")));
    }
}
