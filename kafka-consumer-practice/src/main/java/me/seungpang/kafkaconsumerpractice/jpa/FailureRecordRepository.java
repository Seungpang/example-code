package me.seungpang.kafkaconsumerpractice.jpa;

import me.seungpang.kafkaconsumerpractice.domain.FailureRecord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FailureRecordRepository extends CrudRepository<FailureRecord, Integer> {
    List<FailureRecord> findAllByStatus(String status);
}
