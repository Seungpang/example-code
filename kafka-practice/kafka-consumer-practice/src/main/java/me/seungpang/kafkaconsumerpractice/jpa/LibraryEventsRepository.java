package me.seungpang.kafkaconsumerpractice.jpa;

import me.seungpang.kafkaconsumerpractice.domain.LibraryEvent;
import org.springframework.data.repository.CrudRepository;

public interface LibraryEventsRepository extends CrudRepository<LibraryEvent, Integer> {
}
