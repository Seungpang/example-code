package com.example.helloboot;

import javax.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class HellobootApplication {
    private final JdbcTemplate jdbcTemplate;

    public HellobootApplication(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    void init() {
        jdbcTemplate.execute("create table if not exists hello(name varchar(50) primary key, count int)");
    }

    public static void main(String[] args) {
        //MySplringApplication.run(HellobootApplication.class, args);
        SpringApplication.run(HellobootApplication.class, args);
    }
}
