package com.event.backevents.api.controllers;

import com.event.backevents.api.assembler.EventAssembler;
import com.event.backevents.api.assembler.TicketAssembler;
import com.event.backevents.api.assembler.UserAssembler;
import com.event.backevents.api.model.EventDto;
import com.event.backevents.api.model.EventEditionDto;
import com.event.backevents.api.model.TicketDto;
import com.event.backevents.api.model.UserDto;
import com.event.backevents.domain.model.User;
import com.event.backevents.domain.service.CatalogoUserService;
import com.event.backevents.domain.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final CatalogoUserService catalogoUserService;
    private final UserRepository userRepository;
    private UserAssembler userAssembler;
    private EventAssembler eventAssembler;
    private final TicketAssembler ticketAssembler;

    @GetMapping
    public List<UserDto> listPublisher() {
        return userAssembler.toCollectionModel(userRepository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addPublisher(@Valid @RequestBody User user) {

        User newUser = this.catalogoUserService.add(user);

        return userAssembler.toModel(newUser);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> searchPublisher(@PathVariable Long userId) {

        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }

        return userRepository.findById(userId)
                                    .map(publisher -> ResponseEntity.ok(userAssembler.toModel(publisher)))
                                    .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{userId}/events")
    public ResponseEntity<List<EventDto>> searchPublisherEvents(@PathVariable Long userId) {

        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }

        return userRepository.findById(userId)
                             .map(publisher -> ResponseEntity.ok(eventAssembler.toCollectionModel(publisher.getEventCollection())))
                             .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{userId}/tickets")
    public ResponseEntity<List<TicketDto>> searchPublisherTickets(@PathVariable Long userId) {

        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }

        return userRepository.findById(userId)
                .map(publisher -> ResponseEntity.ok(ticketAssembler.toCollectionModel(publisher.getTicketList())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{publisherId}")
    public ResponseEntity<UserDto> updatePublisher(@PathVariable Long userId,
                                                   @Valid @RequestBody User user) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }

        user.setId(userId);
        user = catalogoUserService.add(user);

        return ResponseEntity.ok(userAssembler.toModel(user));
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<UserDto> patchPublisher(@PathVariable Long userId,
                                                   @Valid @RequestBody User updateUser) {

        if (userRepository.findById(userId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = userRepository.findById(userId).get();

        if(updateUser.getName() != null) {
            user.setName(updateUser.getName());
        }

        if(updateUser.getLocation() != null) {
            user.setLocation(updateUser.getLocation());
        }

        if(updateUser.getCpf() != null) {
            user.setCpf(updateUser.getCpf());
        }

        user = catalogoUserService.add(user);

        return ResponseEntity.ok(userAssembler.toModel(user));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long userId) {

        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }

        catalogoUserService.deleteById(userId);

        return ResponseEntity.noContent().build();
    }
}
