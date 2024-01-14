package me.seungpang.kafkaconsumerpractice.service;

import lombok.extern.slf4j.Slf4j;
import me.seungpang.kafkaconsumerpractice.domain.FailureRecord;
import me.seungpang.kafkaconsumerpractice.jpa.FailureRecordRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FailureService {

    private final FailureRecordRepository failureRecordRepository;

    public FailureService(final FailureRecordRepository failureRecordRepository) {
        this.failureRecordRepository = failureRecordRepository;
    }

    public void saveFailedRecord(final ConsumerRecord<Integer,String> consumerRecord, final Exception e, final String status) {
        var failureRecord = new FailureRecord(
                null,
                consumerRecord.topic(),
                consumerRecord.key(),
                consumerRecord.value(),
                consumerRecord.partition(),
                consumerRecord.offset(),
                e.getCause().getMessage(),
                status
        );

        failureRecordRepository.save(failureRecord);
    }
}
