package me.serungpang.retryexample.domain;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select m from Message m where m.id = :id")
    Optional<Message> findByIdForUpdate(@Param("id") Long id);
}
