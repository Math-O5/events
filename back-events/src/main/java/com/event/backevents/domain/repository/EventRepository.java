package com.event.backevents.domain.repository;

import com.event.eventOn.domain.model.Event;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EventRepository extends CrudRepository<Event, Long> {
    Optional<Event> findByName(String name);
}
