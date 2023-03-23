package com.event.backevents.api.controllers;

import com.event.backevents.domain.model.User;
import com.event.backevents.domain.repository.UserRepository;
import com.event.backevents.domain.service.CatalogoUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class userController {
    private CatalogoUserService catalogoUserService;
    private UserRepository userRepository;

    @GetMapping
    public List<User> listEvents() {
        return userRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@Valid @RequestBody User user) {
        return this.catalogoUserService.save(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> searchUser(@PathVariable Long userId) {
        return userRepository.findById(userId)
                                    .map(ResponseEntity::ok)
                                    .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateEvent(@PathVariable Long userId,
                                             @Valid @RequestBody User user) {
        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }

        user.setId(userId);
        user = catalogoUserService.save(user);

        return ResponseEntity.ok(user);
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
