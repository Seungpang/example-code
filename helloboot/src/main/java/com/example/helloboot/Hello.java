package com.example.helloboot;

public class Hello {
    private String name;
    private int count;

    public Hello(final String name, final int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public int getCount() {
        return count;
    }
}
