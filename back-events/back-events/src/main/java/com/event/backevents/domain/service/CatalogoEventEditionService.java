package com.event.backevents.domain.service;

import com.event.backevents.common.googleGeoLocation.GeoLocationServiceImpl;
import com.event.backevents.domain.model.Event;
import com.event.backevents.domain.model.EventEdition;
import com.event.backevents.domain.model.User;
import com.event.backevents.domain.repository.EventEditionRepository;
import com.event.backevents.domain.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CatalogoEventEditionService {

    @Autowired
    private EventEditionRepository eventEditionRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private GeoLocationServiceImpl geoLocationService;

    @Transactional
    public EventEdition marcar(Long eventId, EventEdition eventEdition) {
        Event event = eventRepository.getReferenceById(eventId);

        EventEdition edth = event.createNewEventEdition(eventEdition);

        // The init date must be in front of current time and in front of end time.
        if(!eventEdition.getInitDate().isBefore(OffsetDateTime.now()) ||
                !eventEdition.getInitDate().isBefore(eventEdition.getEndDate())) {
            throw new RuntimeException("The init date must be after the init date.");
        }

        // Call getGeoCoding to retrieve latitude and longitude
        edth.setLocation(geoLocationService.getGeoCodingForLoc(edth.getLocation()).get());

        return eventEditionRepository.save(edth);
    }
}
