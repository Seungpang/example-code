package com.example.helloboot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class JdbcTemplateTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        jdbcTemplate.execute("create table if not exists hello(name varchar(50) primary key, count int)");
    }

    @Test
    void insertAndQuery() {
        jdbcTemplate.update("insert into hello values(?, ?)", "seungpang", 3);
        jdbcTemplate.update("insert into hello values(?, ?)", "spring", 1);

        final Long count = jdbcTemplate.queryForObject("select count(*) from hello", Long.class);
        assertThat(count).isEqualTo(2);
    }

    @Test
    void insertAndQuery2() {
        jdbcTemplate.update("insert into hello values(?, ?)", "seungpang", 3);
        jdbcTemplate.update("insert into hello values(?, ?)", "spring", 1);

        final Long count = jdbcTemplate.queryForObject("select count(*) from hello", Long.class);
        assertThat(count).isEqualTo(2);
    }
}
