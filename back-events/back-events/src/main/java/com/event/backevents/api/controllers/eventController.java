package com.event.backevents.api.controllers;

import com.event.backevents.domain.model.Event;
import com.event.backevents.domain.repository.EventRepository;
import com.event.backevents.domain.service.CatalogoEventService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/events")
public class eventController {
    private final CatalogoEventService catalogoEventService;
    private final EventRepository eventRepository;

    @GetMapping
    public List<Event> listEvents() {
        return eventRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Event addEvent(@Valid @RequestBody Event event) {
        return this.catalogoEventService.save(event);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> searchClient(@PathVariable Long eventId) {
        return eventRepository.findById(eventId)
                                    .map(ResponseEntity::ok)
                                    .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long eventId,
                                             @Valid @RequestBody Event event) {
        if (!eventRepository.existsById(eventId)) {
            return ResponseEntity.notFound().build();
        }

        event.setId(eventId);
        event = catalogoEventService.save(event);

        return ResponseEntity.ok(event);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            return ResponseEntity.notFound().build();
        }

        catalogoEventService.deleteById(eventId);

        return ResponseEntity.noContent().build();
    }
}
