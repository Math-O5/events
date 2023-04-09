package com.event.backevents.api.controllers;

import com.event.backevents.api.assembler.TicketAssembler;
import com.event.backevents.api.model.TicketDto;
import com.event.backevents.domain.model.Ticket;
import com.event.backevents.domain.repository.TicketRepository;
import com.event.backevents.domain.service.CatalogoTicketService;
import com.event.backevents.domain.repository.EventEditionRepository;
import com.event.backevents.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// /events/{eventId}/th/{editionId}/user/{userId}/ticket
@AllArgsConstructor
@RestController
@RequestMapping("/edition/{editionId}/users/{userId}/ticket")
public class TicketController {
    private final CatalogoTicketService catalogoTicketService;
    private final EventEditionRepository eventEditionRepository;
    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;
    private final TicketAssembler ticketAssembler;

    @PostMapping
    public ResponseEntity<TicketDto> reserverTicket(@PathVariable Long editionId, @PathVariable Long userId) {

        String messageError = "EditionId does not exist: " + Long.toString(editionId);

        if (!eventEditionRepository.existsById(editionId)) {
            return ResponseEntity.notFound().build();
        }

        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }

        Ticket reservedTicket = catalogoTicketService.reservar(editionId, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body( ticketAssembler.toModel(reservedTicket));
    }

    @GetMapping
    public ResponseEntity<TicketDto> retrieveTicket(@PathVariable Long editionId, @PathVariable Long userId) {

        String messageError = "EditionId does not exist: " + Long.toString(editionId);

        if (!eventEditionRepository.existsById(editionId)) {
            return ResponseEntity.notFound().build();
        }

        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }

        Ticket reservedTicket = catalogoTicketService.findByUser(editionId, userId);

        return ResponseEntity.status(HttpStatus.CREATED).body(ticketAssembler.toModel(reservedTicket));
    }

    @PostMapping("/auth")
    public ResponseEntity<Ticket> autorizarTicket(@PathVariable Long editionId, @PathVariable Long userId) {

        String messageError = "EditionId does not exist: " + Long.toString(editionId);

        if (!eventEditionRepository.existsById(editionId)) {
            return ResponseEntity.notFound().build();
        }

        if (!userRepository.existsById(userId)) {
            return ResponseEntity.notFound().build();
        }

        if(catalogoTicketService.autorizar(editionId, userId)) {
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
