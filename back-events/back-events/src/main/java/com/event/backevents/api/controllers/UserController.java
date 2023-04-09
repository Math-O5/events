package com.event.backevents.api.controllers;

import com.event.backevents.api.assembler.UserAssembler;
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

//    @PostMapping("/{publisherId}/event")
//    public ResponseEntity<User> addEventToPublisher(@PathVariable Long publisherId,
//                                                     @Valid @RequestBody Event event) {
//        if (!userRepository.existsById(publisherId)) {
//            return ResponseEntity.notFound().build();
//        }
//
//        publisher.setId(publisherId);
//        publisher = catalogoUserService.save(publisher);
//
//        return ResponseEntity.ok(publisher);
//    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long userId) {
        if (!userRepository.existsById(userId)) { 
            return ResponseEntity.notFound().build();
        }

        catalogoUserService.deleteById(userId);

        return ResponseEntity.noContent().build();
    }
}
