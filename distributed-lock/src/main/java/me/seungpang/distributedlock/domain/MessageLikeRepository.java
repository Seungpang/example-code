package me.seungpang.distributedlock.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageLikeRepository extends JpaRepository<MessageLike, Long> {
}
