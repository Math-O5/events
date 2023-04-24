package com.event.backevents.api.model;

import com.event.backevents.domain.model.EventEdition;
import com.event.backevents.domain.model.Review;
import com.event.backevents.domain.model.User;
import com.event.backevents.domain.model.status.StatusTicket;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketDto {
    private Long id;
    private EventEditionIdDto eventEdition;
    private UserIdDto user;
    private Review review;
    private StatusTicket statusTicket;
}
