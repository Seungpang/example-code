package com.example.Dmaker.repository;

import com.example.Dmaker.entitiy.Developer;
import com.example.Dmaker.entitiy.RetiredDeveloper;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetiredDeveloperRepository extends JpaRepository<RetiredDeveloper, Long> {
}
