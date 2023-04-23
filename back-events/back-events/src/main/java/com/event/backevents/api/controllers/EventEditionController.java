package com.event.backevents.api.controllers;


import com.event.backevents.api.assembler.EventAssembler;
import com.event.backevents.api.assembler.EventEditionAssembler;
import com.event.backevents.api.model.EventEditionDto;
import com.event.backevents.api.model.input.KmOrMiles;
import com.event.backevents.api.model.input.SearchInput;
import com.event.backevents.domain.model.EventEdition;
import com.event.backevents.domain.model.User;
import com.event.backevents.domain.repository.EventEditionRepository;
import com.event.backevents.domain.repository.EventRepository;
import com.event.backevents.domain.repository.UserRepository;
import com.event.backevents.domain.service.BuscaEventEditionService;
import com.event.backevents.domain.service.CatalogoEventEditionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class EventEditionController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventAssembler eventAssembler;

    @Autowired
    private EventEditionRepository eventEditionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CatalogoEventEditionService catalogoEventEditionService;

    @Autowired
    private EventEditionAssembler eventEditionAssembler;

    @Autowired
    private BuscaEventEditionService buscaEventEditionService;

    @GetMapping("/edition")
    public ResponseEntity<List<EventEditionDto>> searchEditions() {
        List<EventEdition> editionList = eventEditionRepository.findAll();

        return ResponseEntity.ok(eventEditionAssembler.toCollectionModel(editionList));
    }

    @GetMapping("edition/nearby/all")
    public ResponseEntity<List<EventEditionDto>> editionsNearByUser(@RequestParam(value = "userId") Long userId,
                                                                    @RequestParam(value = "distance", defaultValue = "10") Integer dist) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty())
            return ResponseEntity.notFound().build();

        Double lat = user.get().getLocation().getLat();
        Double lng = user.get().getLocation().getLng();

        List<EventEdition> editionList = eventEditionRepository.findAllByLocation(lat, lng, dist).get();

        return ResponseEntity.ok(eventEditionAssembler.toCollectionModel(editionList));
    }

    @GetMapping("edition/nearby")
    public ResponseEntity<List<EventEditionDto>> editionsNearByUserId(@RequestParam(value = "userId") Long userId,
                                                                      @RequestParam(value = "init") OffsetDateTime init,
                                                                      @RequestParam(value = "distance", defaultValue = "10") Float distance,
                                                                      @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                      @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {

        Page<EventEdition> editionPage = buscaEventEditionService.eventNearByUser(new SearchInput(userId, init, distance, page, size, KmOrMiles.KM)).get();

        return ResponseEntity.ok(eventEditionAssembler.toCollectionModel(editionPage.getContent()));
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

    @PatchMapping("/events/{eventId}/edition/{editionId}")
    public ResponseEntity<EventEditionDto> updateEvent(@PathVariable Long editionId, @Valid @RequestBody EventEdition updateEventEdition) {

        if (!eventRepository.existsById(editionId)) {
            return ResponseEntity.notFound().build();
        }

        EventEdition eventEdition = eventEditionRepository.getById(editionId);

        EventEdition scheduledEvent = catalogoEventEditionService.marcar(editionId, eventEdition);

        return ResponseEntity.status(HttpStatus.CREATED).body(eventEditionAssembler.toModel(scheduledEvent));
    }


}
