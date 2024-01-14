package me.seungpang.kafkaconsumerpractice.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import me.seungpang.kafkaconsumerpractice.domain.FailureRecord;
import me.seungpang.kafkaconsumerpractice.jpa.FailureRecordRepository;
import me.seungpang.kafkaconsumerpractice.service.LibraryEventsService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RetryScheduler {

    private static final String RETRY = "RETRY";
    private static final String SUCCESS = "SUCCESS";

    private final FailureRecordRepository failureRecordRepository;
    private final LibraryEventsService libraryEventsService;

    public RetryScheduler(final FailureRecordRepository failureRecordRepository,
                          final LibraryEventsService libraryEventsService) {
        this.failureRecordRepository = failureRecordRepository;
        this.libraryEventsService = libraryEventsService;
    }

    @Scheduled(fixedRate = 10000)
    public void retryFailedRecords() {
        log.info("ReTrying Failed Records Started!");
        failureRecordRepository.findAllByStatus(RETRY)
                .forEach(failureRecord -> {
                    log.info("ReTrying Failed Records : {} ", failureRecord);
                    var consumerRecord = buildConsumerRecord(failureRecord);
                    try {
                        libraryEventsService.processLibraryEvent(consumerRecord);
                        failureRecord.updateStatus(SUCCESS);
                        failureRecordRepository.save(failureRecord);
                    } catch (JsonProcessingException e) {
                        log.error("Exception in retryFailedRecords : {} ", e.getMessage(), e);
                    }
                });
        log.info("Retrying Failed Records Completed");
    }

    private ConsumerRecord<Integer, String> buildConsumerRecord(final FailureRecord failureRecord) {

        return new ConsumerRecord<>(
                failureRecord.getTopic(),
                failureRecord.getPartition(),
                failureRecord.getOffsetsValue(),
                failureRecord.getKeyValue(),
                failureRecord.getErrorRecord()
        );
    }
}
