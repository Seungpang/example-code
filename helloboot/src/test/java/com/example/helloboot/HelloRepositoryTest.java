package com.example.helloboot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@HellobootTest
public class HelloRepositoryTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    HelloRepository helloRepository;

    @BeforeEach
    void init() {
        jdbcTemplate.execute("create table if not exists hello(name varchar(50) primary key, count int)");
    }

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
