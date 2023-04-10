package com.event.backevents.domain.repository;

import com.event.backevents.domain.model.EventEdition;
import com.event.backevents.domain.model.Location;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EventEditionRepository extends JpaRepository<EventEdition, Long> {
    Optional<EventEdition> findByName(String name);

    @Query(value="SELECT * FROM EVENT_EDITION E WHERE E.", nativeQuery = true)
    Optional<List<EventEdition>> findAllByLocation(Location location, Pageable pageble);
}
