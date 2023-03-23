package com.event.backevents.domain.service;

import com.event.backevents.domain.model.Event;
import com.event.backevents.domain.repository.EventRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CatalogoEventService {

    @Autowired
    private EventRepository eventRepository;

    public Event findById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new Error("Event não encontrado."));
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Transactional
    public void delete(Event event) {
        eventRepository.delete(event);
    }

    @Transactional
    public void deleteById(Long eventId) {
        eventRepository.deleteById(eventId);
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
