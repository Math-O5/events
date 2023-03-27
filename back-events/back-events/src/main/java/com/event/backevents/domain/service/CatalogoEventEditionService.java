package com.event.backevents.domain.service;

import com.event.backevents.domain.model.Event;
import com.event.backevents.domain.model.EventEdition;
import com.event.backevents.domain.repository.EventEditionRepository;
import com.event.backevents.domain.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CatalogoEventEditionService {
    private EventEditionRepository eventEditionRepository;
    private EventRepository eventRepository;

    @Transactional
    public EventEdition marcar(Long eventId, EventEdition eventEdition) {
        Event event = eventRepository.getReferenceById(eventId);

        return eventEditionRepository.save(event.createNewEventEdition(event, eventEdition));
    }
}
