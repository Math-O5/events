package com.event.backevents.domain.repository;

import com.event.backevents.domain.model.Event;
import com.event.backevents.domain.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Optional<Event> findByName(String name);
}
