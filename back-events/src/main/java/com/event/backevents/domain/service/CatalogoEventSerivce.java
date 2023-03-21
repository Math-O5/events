package com.event.eventOn.domain.service;

import com.event.eventOn.domain.model.Event;
import com.event.eventOn.domain.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CatalogoEventService {

    @Autowired
    private final EventRepository eventRepository;

    public Event findById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new Error("Event não encontrado."));
    }

    public Iterable<Event> findAll() {
        return eventRepository.findAll();
    }
    @Transactional
    public Event save(Event event) {
        boolean nameAlreadyPicked = eventRepository.findByName(event.getName())
                .stream()
                .anyMatch(EventCreated -> !EventCreated.equals(event));
        if(nameAlreadyPicked) {
            throw new Error("Esse nome  já está em uso");
        }

        return eventRepository.save(event);
    }

}
