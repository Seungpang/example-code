package com.example.Dmaker.repository;

import com.example.Dmaker.code.StatusCode;
import com.example.Dmaker.entitiy.Developer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {


    Optional<Developer> findByMemberId(String memberId);

    List<Developer> findDeveloperByStatusCodeEquals(StatusCode statusCode);
}
