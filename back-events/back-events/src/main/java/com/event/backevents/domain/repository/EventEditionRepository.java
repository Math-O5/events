package com.event.backevents.domain.repository;

import com.event.backevents.common.googleGeoLocation.Geometry;
import com.event.backevents.domain.model.EventEdition;
import com.event.backevents.domain.model.Location;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EventEditionRepository extends JpaRepository<EventEdition, Long> {
    Optional<EventEdition> findByName(String name);

    // @Query(value="SELECT * FROM EVENT_EDITION E WHERE E WITHIN(E.location.point, :bounds) = true", nativeQuery = true)

    // 3959 miles
    // 6371 km
    @Query(value="SELECT * FROM EVENT_EDITION E WHERE (" +
            "acos(sin(E.lat * 0.0175) * sin(:location.lat * 0.0175) " +
            "+ cos(E.lat * 0.0175) * cos(:location.lat * 0.0175) *" +
            "cos((:location.lgn * 0.0175) - (E.lgn * 0.0175))" +
            ") * 6371 <= 10" +
            ")", nativeQuery = true)
    Optional<List<EventEdition>> findAllByLocation(@Param("location") Location location);

    // Optional<List<EventEdition>> findAllByLocation(@Param("bounds") Geometry bounds, Pageable pageble);
}
