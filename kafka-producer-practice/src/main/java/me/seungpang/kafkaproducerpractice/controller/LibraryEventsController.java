package me.seungpang.kafkaproducerpractice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import me.seungpang.kafkaproducerpractice.domain.LibraryEvent;
import me.seungpang.kafkaproducerpractice.domain.LibraryEventType;
import me.seungpang.kafkaproducerpractice.producer.LibraryEventsProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@Slf4j
@RestController
public class LibraryEventsController {

    private final LibraryEventsProducer libraryEventsProducer;

    public LibraryEventsController(final LibraryEventsProducer libraryEventsProducer) {
        this.libraryEventsProducer = libraryEventsProducer;
    }

    @PostMapping("/v1/libraryevent")
    public ResponseEntity<LibraryEvent> postLibraryEvent(
            @RequestBody @Valid LibraryEvent libraryEvent
    ) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        log.info("libraryEvent : {} ", libraryEvent);
        // invoke the kafka producer
        // libraryEventsProducer.sendLibraryEvent(libraryEvent);
        // libraryEventsProducer.sendLibraryEvent2(libraryEvent);
        libraryEventsProducer.sendLibraryEvent3(libraryEvent);

        log.info("After Sending libraryEvent : ");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(libraryEvent);
    }

    @PutMapping("/v1/libraryevent")
    public ResponseEntity<?> putLibraryEvent(
            @RequestBody @Valid LibraryEvent libraryEvent
    ) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        log.info("libraryEvent : {} ", libraryEvent);

        final ResponseEntity<String> BAD_REQUEST = validateLibraryEvent(libraryEvent);
        if (BAD_REQUEST != null) return BAD_REQUEST;

        libraryEventsProducer.sendLibraryEvent3(libraryEvent);

        log.info("After Sending libraryEvent : ");
        return ResponseEntity.status(HttpStatus.OK)
                .body(libraryEvent);
    }

    private static ResponseEntity<String> validateLibraryEvent(final LibraryEvent libraryEvent) {
        if (libraryEvent.libraryEventId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("LibraryEventId를 입력해야합니다.");
        }

        if (!libraryEvent.libraryEventType().equals(LibraryEventType.UPDATE)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("LibraryEventType에서 UPDATE 경우만 지원합니다.");
        }
        return null;
    }
}
