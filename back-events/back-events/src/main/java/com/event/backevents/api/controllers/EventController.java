package com.event.backevents.api.controllers;

import com.event.backevents.api.assembler.EventAssembler;
import com.event.backevents.api.exceptionhandler.ResourceNotFoundException;
import com.event.backevents.api.exceptionhandler.RestResponseEntityExceptionHandler;
import com.event.backevents.api.model.EventDto;
import com.event.backevents.domain.model.Event;
import com.event.backevents.domain.repository.EventRepository;
import com.event.backevents.domain.repository.UserRepository;
import com.event.backevents.domain.service.CatalogoEventService;
import com.event.backevents.domain.service.MarcarEventService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@RestController
@Data
@RequestMapping("/events")
public class EventController {

    @Autowired
    private CatalogoEventService catalogoEventService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MarcarEventService marcarEventService;

    @Autowired
    private EventAssembler eventAssembler;

    @Operation(summary = "Get all events")
    @GetMapping
    public List<EventDto> listEvents() {
        return eventAssembler.toCollectionModel(eventRepository.findAll());
    }

    @Operation(summary = "Create a event")
    @PostMapping
    public ResponseEntity<EventDto> addEvent(@Valid @RequestBody Event event) {


        if (!userRepository.existsById(event.getUser().getId())) {
            throw new ResourceNotFoundException("This user does not exist.", null, true, false);
        }

        Event scheduledEvent = this.marcarEventService.marcar(event);

        return ResponseEntity.status(HttpStatus.CREATED).body(eventAssembler.toModel(scheduledEvent));
    }

    @Operation(summary = "Search an event by its id.")
    @GetMapping("/{eventId}")
    public ResponseEntity<EventDto> searchClient(@PathVariable Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            return ResponseEntity.notFound().build();
        }

        return eventRepository.findById(eventId)
                                    .map(event -> ResponseEntity.ok(eventAssembler.toModel(event)))
                                    .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update an event by its id.")
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

    @Operation(summary = "Update one or more field(s) of event by its id.")
    @PatchMapping("/{eventId}")
    public ResponseEntity<EventDto> patchEvent(@PathVariable Long eventId,
                                                @Valid @RequestBody Event updateEvent) {

        if (!eventRepository.existsById(eventId)) {
            return ResponseEntity.notFound().build();
        }

        Event event = eventRepository.getById(eventId);

        if(updateEvent.getName() != null) {
            event.setName(updateEvent.getName());
        }

        if(updateEvent.getIsActive() != null) {
            event.setIsActive(updateEvent.getIsActive());
        }

        if(updateEvent.getDescricao() != null) {
            event.setDescricao(updateEvent.getDescricao());
        }

        event = catalogoEventService.save(event);

        return ResponseEntity.ok(eventAssembler.toModel(event));
    }

    @Operation(summary = "Delete an event by it's id.")
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            return ResponseEntity.notFound().build();
        }

        catalogoEventService.deleteById(eventId);

        return ResponseEntity.noContent().build();
    }
}
