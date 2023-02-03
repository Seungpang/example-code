package com.example.helloboot;

public class HelloController {
    public String hello(String name) {
        final SimpleHelloService simpleHelloService = new SimpleHelloService();

        return simpleHelloService.sayHello(name);
    }
}
