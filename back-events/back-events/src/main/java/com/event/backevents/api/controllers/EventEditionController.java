package com.event.backevents.api.controllers;


import com.event.backevents.api.assembler.EventAssembler;
import com.event.backevents.api.assembler.EventEditionAssembler;
import com.event.backevents.api.model.EventDto;
import com.event.backevents.api.model.EventEditionDto;
import com.event.backevents.domain.model.Event;
import com.event.backevents.domain.model.EventEdition;
import com.event.backevents.domain.repository.EventRepository;
import com.event.backevents.domain.service.CatalogoEventEditionService;
import com.event.backevents.domain.service.CatalogoEventService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/events/{eventId}/edition")
public class EventEditionController {

    private EventRepository eventRepository;
    private EventAssembler eventAssembler;

    private CatalogoEventEditionService catalogoEventEditionService;
    private EventEditionAssembler eventEditionAssembler;

    @GetMapping
    public ResponseEntity<List<EventEditionDto>> searchEventEdition(@PathVariable Long eventId) {
        return eventRepository.findById(eventId)
                .map(event -> ResponseEntity.ok(eventEditionAssembler.toCollectionModel(event.getEventEditionCollection())))
                .orElse(ResponseEntity.notFound().build());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public EventEditionDto addEvent(@PathVariable Long eventId, @Valid @RequestBody EventEdition eventEdition) {

        if (!eventRepository.existsById(eventId)) {
//            return ResponseEntity.notFound().build();
        }

        EventEdition scheduledEvent = catalogoEventEditionService.marcar(eventId, eventEdition);

        return eventEditionAssembler.toModel(scheduledEvent);
    }
}
