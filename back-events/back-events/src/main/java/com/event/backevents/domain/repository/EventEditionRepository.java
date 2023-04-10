package com.event.backevents.domain.repository;

import com.event.backevents.common.googleGeoLocation.Geometry;
import com.event.backevents.domain.model.EventEdition;
import com.event.backevents.domain.model.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

public interface EventEditionRepository extends JpaRepository<EventEdition, Long> {
    Optional<EventEdition> findByName(String name);

    // @Query(value="SELECT * FROM EVENT_EDITION E WHERE E WITHIN(E.location.point, :bounds) = true", nativeQuery = true)

    // 3959 miles
    // 6371 km
    // https://stackoverflow.com/questions/7783684/select-coordinates-which-fall-within-a-radius-of-a-central-point
    @Query(value="SELECT * FROM EVENT_EDITION E WHERE (" +
            "acos(sin(E.lat * 0.0175) * sin(:lat * 0.0175) " +
            "+ cos(E.lat * 0.0175) * cos(:lat * 0.0175) *" +
            "cos((:lng * 0.0175) - (E.lng * 0.0175))" +
            ") * 6371 <= :distance" +
            ")",
            nativeQuery = true)
    Optional<List<EventEdition>> findAllByLocation(@Param("lat") Double lat, @Param("lng") Double lng,
                                                   @RequestParam(value="distance", defaultValue="10") Integer distance);

    @Query(value="SELECT * FROM EVENT_EDITION E WHERE (" +
            "acos(sin(E.lat * 0.0175) * sin(:lat * 0.0175) " +
            "+ cos(E.lat * 0.0175) * cos(:lat * 0.0175) *" +
            "cos((:lng * 0.0175) - (E.lng * 0.0175))" +
            ") * 6371 <= :distance" +
            ")",
            countQuery="SELECT COUNT(id) FROM EVENT_EDITION E WHERE (" +
                    "acos(sin(E.lat * 0.0175) * sin(:lat * 0.0175) " +
                    "+ cos(E.lat * 0.0175) * cos(:lat * 0.0175) *" +
                    "cos((:lng * 0.0175) - (E.lng * 0.0175))" +
                    ") * 6371 <= :distance" +
                    ")",
            nativeQuery = true)
    Optional<Page<EventEdition>> findAllByLocationPagination(@Param("lat") Double lat, @Param("lng") Double lng,
                                                   @RequestParam(value="distance", defaultValue="10") Integer distance,
                                                   Pageable page);

    // Optional<List<EventEdition>> findAllByLocation(@Param("bounds") Geometry bounds, Pageable pageble);
}
