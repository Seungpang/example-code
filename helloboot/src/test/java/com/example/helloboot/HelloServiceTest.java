package com.example.helloboot;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class HelloServiceTest {

    @Test
    void simpleHelloService() {
        final SimpleHelloService helloService = new SimpleHelloService();

        final String ret = helloService.sayHello("Test");

        assertThat(ret).isEqualTo("Hello Test");
    }

    @Test
    void helloDecorator() {
        final HelloDecorator decorator = new HelloDecorator(name -> name);

        final String ret = decorator.sayHello("Test");

        assertThat(ret).isEqualTo("*Test*");
    }
}

