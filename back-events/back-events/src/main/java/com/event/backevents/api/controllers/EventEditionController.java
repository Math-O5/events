package com.event.backevents.api.controllers;


import com.event.backevents.api.assembler.EventAssembler;
import com.event.backevents.api.assembler.EventEditionAssembler;
import com.event.backevents.api.model.EventEditionDto;
import com.event.backevents.domain.model.EventEdition;
import com.event.backevents.domain.repository.EventEditionRepository;
import com.event.backevents.domain.repository.EventRepository;
import com.event.backevents.domain.service.CatalogoEventEditionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
public class EventEditionController {

    private EventRepository eventRepository;
    private EventAssembler eventAssembler;
    private EventEditionRepository eventEditionRepository;

    private CatalogoEventEditionService catalogoEventEditionService;
    private EventEditionAssembler eventEditionAssembler;

    @GetMapping("/edition")
    public ResponseEntity<List<EventEditionDto>> searchEditions() {
        List<EventEdition> editionList = eventEditionRepository.findAll();

        return ResponseEntity.ok(eventEditionAssembler.toCollectionModel(editionList));
    }

    @GetMapping("/events/{eventId}/edition")
    public ResponseEntity<List<EventEditionDto>> searchEventEdition(@PathVariable Long eventId) {
        return eventRepository.findById(eventId)
                .map(event -> ResponseEntity.ok(eventEditionAssembler.toCollectionModel(event.getEventEditionCollection())))
                .orElse(ResponseEntity.notFound().build());
    }

    //@ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/events/{eventId}/edition")
    public ResponseEntity<EventEditionDto> addEvent(@PathVariable Long eventId, @Valid @RequestBody EventEdition eventEdition) {

        if (!eventRepository.existsById(eventId)) {
            return ResponseEntity.notFound().build();
        }

        EventEdition scheduledEvent = catalogoEventEditionService.marcar(eventId, eventEdition);

        return ResponseEntity.status(HttpStatus.CREATED).body(eventEditionAssembler.toModel(scheduledEvent));
    }


}
