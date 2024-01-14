package me.seungpang.kafkaconsumerpractice.jpa;

import me.seungpang.kafkaconsumerpractice.domain.FailureRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@DataJpaTest
class FailureRecordRepositoryTest {

    @Autowired
    private FailureRecordRepository failureRecordRepository;

    @BeforeEach
    public void setUp() {
        var record = "{\"libraryEventId\":1,\"book\":{\"bookId\":456,\"bookName\":\"Kafka Using Spring Boot 2.X\",\"bookAuthor\":\"Dilip\"}}";

        var failureRecord = new FailureRecord(null, "library-events", 123, record, 1, 0L, "exception occurred", "RETRY");
        var failureRecord1 = new FailureRecord(null, "library-events", 123, record, 1, 1L, "exception occurred", "DEAD");

        failureRecordRepository.saveAll(List.of(failureRecord, failureRecord1));
    }

    @Test
    void findAllByStatus() {
        //when
        var failRecordList = failureRecordRepository.findAllByStatus("RETRY");

        //then
        assertEquals(1, failRecordList.size());
    }
}
