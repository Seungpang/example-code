package me.seungpang.webClient;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final WebClientService webClientService;

    @GetMapping("/data")
    public Mono<String> getData() {
        return webClientService.getData();
    }

    @GetMapping("/test")
    public String test() throws InterruptedException {
        Thread.sleep(5000);
        return "test";
    }

    @GetMapping("/combined-data")
    public Mono<String> getCombinedData() {
        Mono<String> data1 = webClientService.getData();
        Mono<String> data2 = webClientService.getData(); // 다른 URL로 변경 가능

        return Mono.zip(data1, data2, (d1, d2) -> "Combined Data: " + d1 + " & " + d2);
    }
}
