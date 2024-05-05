package me.seungpang.distributedlock.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;
import java.util.Map;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Map<String, Integer> map = new HashMap<>();

    public static void main(String[] args) {
        char c = 'c';
        String a = String.valueOf(c);
    }
}
