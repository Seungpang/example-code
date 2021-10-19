package com.seungpang.testspringbatch.core.repository;

import com.seungpang.testspringbatch.core.domain.PlainText;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlainTextRepository extends JpaRepository<PlainText, Integer> {

    Page<PlainText> findBy(Pageable pageable);
}
