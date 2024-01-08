package me.seungpang.kafkaconsumerpractice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.seungpang.kafkaconsumerpractice.domain.Book;
import me.seungpang.kafkaconsumerpractice.domain.LibraryEvent;
import me.seungpang.kafkaconsumerpractice.domain.LibraryEventType;
import me.seungpang.kafkaconsumerpractice.jpa.LibraryEventsRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LibraryEventsService {

    private final ObjectMapper objectMapper;
    private final LibraryEventsRepository libraryEventsRepository;

    public LibraryEventsService(ObjectMapper objectMapper, LibraryEventsRepository libraryEventsRepository) {
        this.objectMapper = objectMapper;
        this.libraryEventsRepository = libraryEventsRepository;
    }

    public void processLibraryEvent(ConsumerRecord<Integer, String> consumerRecord) throws JsonProcessingException {
        LibraryEvent libraryEvent = objectMapper.readValue(consumerRecord.value(), LibraryEvent.class);
        log.info("libraryEvent : {} ", libraryEvent);

        if (libraryEvent.getLibraryEventType() == LibraryEventType.UPDATE) {
            validate(libraryEvent);
        }

        save(libraryEvent);
    }

    private void validate(final LibraryEvent libraryEvent) {
        if (libraryEvent.getLibraryEventId() == null) {
            throw new IllegalArgumentException("Library Event Id is missing");
        }

        libraryEventsRepository.findById(libraryEvent.getLibraryEventId())
                .orElseThrow(() -> new IllegalArgumentException("Library Event Id is missing"));
        log.info("Validation is successful for the library Event : {} ", libraryEvent);
    }

    private void save(final LibraryEvent libraryEvent) {
        Book book = libraryEvent.getBook();
        book.updateLibraryEvent(libraryEvent);
        libraryEventsRepository.save(libraryEvent);
        log.info(("Successfully Persisted the library Event {} "), libraryEvent);
    }
}
