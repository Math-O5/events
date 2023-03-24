package com.event.backevents.api.controllers;

import com.event.backevents.domain.model.Publisher;
import com.event.backevents.domain.repository.PublisherRepository;
import com.event.backevents.domain.service.CatalogoPublisherService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/publishers")
public class publisherController {
    private final CatalogoPublisherService catalogoPublisherService;
    private final PublisherRepository publisherRepository;

    @GetMapping
    public List<Publisher> listPublisher() {
        return publisherRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Publisher addPublisher(@Valid @RequestBody Publisher publisher) {
        return this.catalogoPublisherService.save(publisher);
    }

    @GetMapping("/{publisherId}")
    public ResponseEntity<Publisher> searchPublisher(@PathVariable Long publisherId) {
        return publisherRepository.findById(publisherId)
                                    .map(ResponseEntity::ok)
                                    .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{publisherId}")
    public ResponseEntity<Publisher> updatePublisher(@PathVariable Long publisherId,
                                             @Valid @RequestBody Publisher publisher) {
        if (!publisherRepository.existsById(publisherId)) {
            return ResponseEntity.notFound().build();
        }

        publisher.setId(publisherId);
        publisher = catalogoPublisherService.save(publisher);

        return ResponseEntity.ok(publisher);
    }

    @DeleteMapping("/{publisherId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long publisherId) {
        if (!publisherRepository.existsById(publisherId)) {
            return ResponseEntity.notFound().build();
        }

        catalogoPublisherService.deleteById(publisherId);

        return ResponseEntity.noContent().build();
    }

}
