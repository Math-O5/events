package com.event.backevents.domain.model;


import com.event.backevents.api.exceptionhandler.NoAvailableTicketException;
import com.event.backevents.api.exceptionhandler.TicketException;
import com.event.backevents.domain.model.status.StatusTicket;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class Ticket extends BaseEntity {

    @ManyToOne
    private EventEdition eventEdition;

    @ManyToOne
    private User user;

    @OneToOne(mappedBy = "ticket")
    private Review review;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Enumerated(EnumType.STRING)
    private StatusTicket statusTicket;

    public void useTicket() {
        if(this.getStatusTicket().equals(StatusTicket.BOOKED)) {
            this.setStatusTicket(StatusTicket.USED);
        }
    }

    public void expireTicket() {
        if(this.getStatusTicket().equals(StatusTicket.BOOKED)) {
            this.setStatusTicket(StatusTicket.EXPIRED);
        }
    }

    public void reviewTicket(Review reviewParam) {
        Review review = new Review(reviewParam);

        // Check if the user can review the event
        if(this.getStatusTicket().equals(StatusTicket.USED)) {
            this.setReview(review);
        }
    }
}
