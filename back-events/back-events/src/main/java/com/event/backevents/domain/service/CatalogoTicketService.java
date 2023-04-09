package com.event.backevents.domain.service;

import com.event.backevents.api.exceptionhandler.NoAvailableTicketException;
import com.event.backevents.api.exceptionhandler.TicketException;
import com.event.backevents.domain.model.EventEdition;
import com.event.backevents.domain.model.Review;
import com.event.backevents.domain.model.User;
import com.event.backevents.domain.model.Ticket;
import com.event.backevents.domain.model.status.StatusTicket;
import com.event.backevents.domain.repository.EventEditionRepository;
import com.event.backevents.domain.repository.EventRepository;
import com.event.backevents.domain.repository.TicketRepository;
import com.event.backevents.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Data
public class CatalogoTicketService {
    private final TicketRepository ticketRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventEditionRepository eventEditionRepository;

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Ticket findByUser(Long editionId, Long userId) {
        Optional<EventEdition> edition = eventEditionRepository
                .findById(editionId);

        Optional<User> user = userRepository
                .findById(userId);

        Ticket reservedTicket;

        if(!edition.isPresent()) {
            throw new Error("This edition does not exist: " + Long.toString(editionId));
        }

        if(!user.isPresent()) {
            throw new Error("User does not exist: " + Long.toString(userId));
        }

        // Get the ticket from the Event list edition
        reservedTicket = edition.get().getTicketList()
                .stream()
                .filter(ticket -> ticket.getUser().equals(user.get()))
                .findAny()
                .orElseThrow(() -> new NoAvailableTicketException("No ticket booked for user in this edition."));

        return reservedTicket;
    }

    @Transactional
    public Ticket reservar(Long editionId, Long userId) {
        Optional<EventEdition> edition = eventEditionRepository
                .findById(editionId);

        Optional<User> user = userRepository
                .findById(userId);

        Ticket reservedTicket;

        if(!edition.isPresent()) {
            throw new Error("This edition does not exist: " + Long.toString(editionId));
        }

        if(!user.isPresent()) {
            throw new Error("User does not exist: " + Long.toString(userId));
        }

        // look if the user already booked this edition
        // Get the ticket from the Event list edition
        Boolean alreadyExist = edition.get().getTicketList()
                .stream()
                .filter(ticket -> ticket.getUser().equals(user.get()))
                .findAny()
                .isPresent();

        if (alreadyExist)
            throw new NoAvailableTicketException("The user alreasy booked this event.");

        reservedTicket = edition.get().createTicket(user.get())
                .orElseThrow(() -> new NoAvailableTicketException("All tickets booked."));

        return ticketRepository.save(reservedTicket);
    }

    @Transactional
    public Boolean autorizar(Long editionId, Long userId) {
        Optional<EventEdition> edition = eventEditionRepository
                .findById(editionId);

        Optional<User> user = userRepository
                .findById(userId);

        Ticket reservedTicket;

        if(!edition.isPresent()) {
            throw new Error("This edition does not exist: " + Long.toString(editionId));
        }

        if(!user.isPresent()) {
            throw new Error("User does not exist: " + Long.toString(userId));
        }

        // Get the ticket from the Event list edition
        reservedTicket = edition.get().getTicketList()
                .stream()
                .filter(ticket -> ticket.getUser().equals(user.get()))
                .findAny()
                .orElseThrow(() -> new NoAvailableTicketException("No ticket booked for user in this edition."));

        // If the ticket is not booked
        if(!reservedTicket.getStatusTicket().equals(StatusTicket.BOOKED))
            return false;

        reservedTicket.useTicket();

        return reservedTicket.getStatusTicket().equals(StatusTicket.USED);
    }


    @Transactional
    public void review(Long editionId, Long userId, Review review) {
        Optional<EventEdition> edition = eventEditionRepository
                .findById(editionId);

        Optional<User> user = userRepository
                .findById(userId);

        Ticket reservedTicket;

        if(!edition.isPresent()) {
            throw new Error("This edition does not exist: " + Long.toString(editionId));
        }

        if(!user.isPresent()) {
            throw new Error("User does not exist: " + Long.toString(userId));
        }

        // Get the ticket from the Event list edition
        reservedTicket = edition.get().getTicketList()
                .stream()
                .filter(ticket -> ticket.getUser().equals(user.get()))
                .findAny()
                .orElseThrow(() -> new NoAvailableTicketException("No ticket booked for user in this edition."));

        reservedTicket.setReview(review);

    }
}
