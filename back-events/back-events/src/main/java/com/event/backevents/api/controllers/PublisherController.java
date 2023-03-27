package com.event.backevents.api.controllers;

import com.event.backevents.api.assembler.PublisherAssembler;
import com.event.backevents.api.model.PublisherDto;
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
public class PublisherController {
    private final CatalogoPublisherService catalogoPublisherService;
    private final PublisherRepository publisherRepository;
    private PublisherAssembler publisherAssembler;

    @GetMapping
    public List<PublisherDto> listPublisher() {
        return publisherAssembler.toCollectionModel(publisherRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PublisherDto addPublisher(@Valid @RequestBody Publisher publisher) {

        Publisher newPublisher = this.catalogoPublisherService.add(publisher);

        return publisherAssembler.toModel(newPublisher);
    }

    @GetMapping("/{publisherId}")
    public ResponseEntity<PublisherDto> searchPublisher(@PathVariable Long publisherId) {
        if (!publisherRepository.existsById(publisherId)) {
            return ResponseEntity.notFound().build();
        }

        return publisherRepository.findById(publisherId)
                                    .map(publisher -> ResponseEntity.ok(publisherAssembler.toModel(publisher)))
                                    .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{publisherId}")
    public ResponseEntity<PublisherDto> updatePublisher(@PathVariable Long publisherId,
                                             @Valid @RequestBody Publisher publisher) {
        if (!publisherRepository.existsById(publisherId)) {
            return ResponseEntity.notFound().build();
        }

        publisher.setId(publisherId);
        publisher = catalogoPublisherService.add(publisher);

        return ResponseEntity.ok(publisherAssembler.toModel(publisher));
    }

//    @PostMapping("/{publisherId}/event")
//    public ResponseEntity<Publisher> addEventToPublisher(@PathVariable Long publisherId,
//                                                     @Valid @RequestBody Event event) {
//        if (!publisherRepository.existsById(publisherId)) {
//            return ResponseEntity.notFound().build();
//        }
//
//        publisher.setId(publisherId);
//        publisher = catalogoPublisherService.save(publisher);
//
//        return ResponseEntity.ok(publisher);
//    }

    @DeleteMapping("/{publisherId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long publisherId) {
        if (!publisherRepository.existsById(publisherId)) {
            return ResponseEntity.notFound().build();
        }

        catalogoPublisherService.deleteById(publisherId);

        return ResponseEntity.noContent().build();
    }
}
