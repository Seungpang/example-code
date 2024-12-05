package me.seungpang.distributedlock.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.HashMap;
import java.util.Map;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
