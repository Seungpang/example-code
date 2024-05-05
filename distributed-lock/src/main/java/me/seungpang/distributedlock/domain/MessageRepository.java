package me.seungpang.distributedlock.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("select m from Message m where m.id = :id")
    Optional<Message> findByIdForUpdate(@Param("id") Long id);
}
