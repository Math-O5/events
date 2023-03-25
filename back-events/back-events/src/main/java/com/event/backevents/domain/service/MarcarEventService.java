package com.event.backevents.domain.service;


import com.event.backevents.domain.model.Event;
import com.event.backevents.domain.model.Publisher;
import com.event.backevents.domain.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class MarcarEventService {

    private CatalogoPublisherService catalogoPublisherService;
    private EventRepository eventRepository;

    @Transactional
    public Event marcar(Event event) {
        //Publisher publisher = catalogoPublisherService.findById(event.getPublisher().getId());

        // event.setPublisher(publisher);
        // publisher.getEventCollection().add(event);
        return event;
       // return eventRepository.save(event);
    }

}
