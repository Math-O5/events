package com.event.backevents.controllers;

import com.event.backevents.domain.model.Event;
import com.event.backevents.domain.repository.EventRepository;
import com.event.backevents.domain.service.CatalogoEventService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/events")
public class eventController {
    @Autowired
    private final CatalogoEventService catalogoEventService;

    @GetMapping
    public Iterable<Event> list() {
        return catalogoEventService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Event addEvent(@Valid @RequestBody Event event) {
        return this.catalogoEventService.save(event);
    }
}
