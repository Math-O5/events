package com.event.backevents.api.controllers;

import com.event.backevents.api.assembler.EventAssembler;
import com.event.backevents.api.model.EventDto;
import com.event.backevents.domain.model.Event;
import com.event.backevents.domain.repository.EventRepository;
import com.event.backevents.domain.service.CatalogoEventService;
import com.event.backevents.domain.service.MarcarEventService;
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
    private final MarcarEventService marcarEventService;
    private EventAssembler eventAssembler;

    @GetMapping
    public List<EventDto> listEvents() {
        return eventAssembler.toCollectionModel(eventRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventDto addEvent(@Valid @RequestBody Event event) {

        Event scheduledEvent = this.marcarEventService.marcar(event);

        return eventAssembler.toModel(scheduledEvent);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDto> searchClient(@PathVariable Long eventId) {
        return eventRepository.findById(eventId)
                                    .map(event -> ResponseEntity.ok(eventAssembler.toModel(event)))
                                    .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<EventDto> updateEvent(@PathVariable Long eventId,
                                             @Valid @RequestBody Event event) {
        if (!eventRepository.existsById(eventId)) {
            return ResponseEntity.notFound().build();
        }

        event.setId(eventId);
        event = catalogoEventService.save(event);

        return ResponseEntity.ok(eventAssembler.toModel(event));
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
