package com.event.backevents.domain.service;


import com.event.backevents.domain.model.Event;
import com.event.backevents.domain.model.Publisher;
import com.event.backevents.domain.repository.EventRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
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

        System.out.println(event.getName());

        Publisher publisher = catalogoPublisherService.findById(event.getPublisher().getId());

        System.out.println("Flag");

        if(publisher == null)
            throw new RuntimeException("Publisher does not exist");

        System.out.println("Flag2: " +  publisher.toString());
        System.out.println("Flag2: " +  publisher.getEventCollection().toString());

        publisher.getEventCollection().add(event);

        System.out.println("Flag2: " +  publisher.getName());

        Event newEvent = new Event();
        newEvent.setPublisher(publisher);
        newEvent.setName(event.getName());

        System.out.println(event.getName());
        // System.out.println("Flag3: " +  event);
        System.out.println("Flag3: " +  event.getPublisher().toString());

        return eventRepository.save(newEvent);
    }

}
