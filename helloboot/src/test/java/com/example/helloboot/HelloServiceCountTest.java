package com.example.helloboot;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
class HelloServiceCountTest {
    @Autowired
    HelloService helloService;

    @Autowired
    HelloRepository helloRepository;


    @Test
    void sayHelloIncreaseCount() {
        IntStream.rangeClosed(1, 10).forEach(count -> {
            helloService.sayHello("seungpang");
            assertThat(helloRepository.countOf("seungpang")).isEqualTo(count);
        });
    }
}
