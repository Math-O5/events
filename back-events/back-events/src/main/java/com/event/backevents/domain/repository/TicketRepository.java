package com.event.backevents.domain.repository;

import com.event.backevents.domain.model.Ticket;
import com.event.backevents.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
