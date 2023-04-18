package com.event.backevents.domain.model;

import com.event.backevents.api.exceptionhandler.NoAvailableTicketException;
import com.event.backevents.api.model.LocationDto;
import com.event.backevents.domain.model.status.StatusTicket;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;
import java.util.*;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
public class EventEdition extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private Integer qtdTickets;
    private Integer qtdLockedTickets;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Float rate;

    @OneToMany(mappedBy = "eventEdition", cascade = CascadeType.ALL)
    private List<Ticket> ticketList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(nullable = false)
    private Event event;

    @Embedded
    private Location location;

    @Valid @NotBlank
    private OffsetDateTime initDate;

    @Valid @NotBlank
    private OffsetDateTime endDate;

    public String toString() {
        return "EventEdition{" +
                "name='" + name + '\'' +
                ", desc='" + descricao + '\'' +
                ", rate=" + rate +
                '}';
    }

    /*
        This function add to the ticketList a ticket and increases the registered ammount
        of tickets booked.
     */
    public void addBookTicket(Ticket ticket) {
        this.getTicketList().add(ticket);
        this.setQtdLockedTickets(this.getQtdLockedTickets() + 1);
    }

    public Optional<Ticket> createTicket(User user) throws NoAvailableTicketException{
        Optional<Ticket> ticket = Optional.empty();

        // Return empty ticket
        if (this.getQtdLockedTickets() >= this.getQtdTickets()) {
            return ticket;
        }

        ticket = Optional.of(new Ticket());
        ticket.get().setEventEdition(this);
        ticket.get().setUser(user);
        ticket.get().setStatusTicket(StatusTicket.BOOKED);

        // Add this ticket to the booked User ticket list
        user.getTicketList().add(ticket.get());

        // Add this ticket to the booked ticket list
        this.addBookTicket(ticket.get());

        return ticket;
    }

}
