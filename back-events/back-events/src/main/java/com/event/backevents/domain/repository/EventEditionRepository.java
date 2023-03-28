package com.event.backevents.domain.repository;

import com.event.backevents.domain.model.EventEdition;
import com.event.backevents.domain.model.Location;
import com.event.backevents.domain.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventEditionRepository extends JpaRepository<EventEdition, Long> {
    Optional<EventEdition> findByName(String name);
}
