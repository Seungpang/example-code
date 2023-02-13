package com.example.helloboot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@Transactional
class HelloRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    HelloRepository helloRepository;

    @Test
    void findHelloFailed() {
        assertThat(helloRepository.findHello("seungpang")).isNull();
    }

    @Test
    void increaseCount() {
        assertThat(helloRepository.countOf("seungpang")).isZero();

        helloRepository.increaseCount("seungpang");
        assertThat(helloRepository.countOf("seungpang")).isEqualTo(1);

        helloRepository.increaseCount("seungpang");
        assertThat(helloRepository.countOf("seungpang")).isEqualTo(2);
    }
}
