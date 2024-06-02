package me.seungpang.spring_assert;

import org.springframework.util.Assert;

public class Member {

    private final Long id;
    private final String name;
    private final int age;

    public Member(final Long id, final String name, final int age) {
        Assert.notNull(name, "Member name is not null"); // message 생성 비용
        Assert.notNull(name, () -> "Member name is not null"); // 실제 null일 경우에만 message를 생성
        this.id = id;
        this.name = name;
        this.age = age;
    }
}
