package me.seungpang.kafkaproducerpractice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import me.seungpang.kafkaproducerpractice.domain.LibraryEvent;
import me.seungpang.kafkaproducerpractice.producer.LibraryEventsProducer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
            @RequestBody LibraryEvent libraryEvent
    ) throws JsonProcessingException, ExecutionException, InterruptedException, TimeoutException {
        log.info("libraryEvent : {} ", libraryEvent);
        // invoke the kafka producer
        // libraryEventsProducer.sendLibraryEvent(libraryEvent);
        libraryEventsProducer.sendLibraryEvent2(libraryEvent);

        log.info("After Sending libraryEvent : ");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(libraryEvent);
    }
}
