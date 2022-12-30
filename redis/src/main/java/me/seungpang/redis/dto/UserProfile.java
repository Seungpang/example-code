package me.seungpang.redis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserProfile {

    @JsonProperty
    private String name;

    @JsonProperty
    private int age;

    public UserProfile(final String name, final int age) {
        this.name = name;
        this.age = age;
    }
}
