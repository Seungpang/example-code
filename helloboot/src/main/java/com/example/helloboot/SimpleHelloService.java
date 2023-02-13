package com.example.helloboot;

import org.springframework.stereotype.Service;

@Service
public class SimpleHelloService implements HelloService {
    private final HelloRepository helloRepository;

    public SimpleHelloService(final HelloRepository helloRepository) {
        this.helloRepository = helloRepository;
    }

    @Override
    public String sayHello(final String name) {
        this.helloRepository.increaseCount(name);

        return "Hello " + name;
    }

    @Override
    public int countOf(final String name) {
        return helloRepository.countOf(name);
    }
}
