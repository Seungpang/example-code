package com.example.helloboot;

import com.example.config.MySpringBootApplication;
import org.springframework.boot.SpringApplication;

@MySpringBootApplication
public class HellobootApplication {
    public static void main(String[] args) {
        //MySplringApplication.run(HellobootApplication.class, args);
        SpringApplication.run(HellobootApplication.class, args);
    }
}
