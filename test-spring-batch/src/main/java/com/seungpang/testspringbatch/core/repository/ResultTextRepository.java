package com.seungpang.testspringbatch.core.repository;

import com.seungpang.testspringbatch.core.domain.ResultText;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultTextRepository extends JpaRepository<ResultText, Integer> {
}
