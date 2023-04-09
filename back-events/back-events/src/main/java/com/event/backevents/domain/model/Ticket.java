package com.event.backevents.domain.model;


import com.event.backevents.api.exceptionhandler.NoAvailableTicketException;
import com.event.backevents.api.exceptionhandler.TicketException;
import com.event.backevents.domain.model.status.StatusTicket;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@SuperBuilder
public class Ticket extends BaseEntity {

    @ManyToOne
    private EventEdition eventEdition;

    @ManyToOne
    private User user;

    @OneToOne(mappedBy = "ticket")
    private Review review;

    private StatusTicket statusTicket;

    public void useTicket() {
        if(this.getStatusTicket().equals(StatusTicket.BOOKED)) {
            this.setStatusTicket(StatusTicket.USED);
        }

        throw new NoAvailableTicketException("The ticket was not booked.");
    }

    public void reviewTicket(Review reviewParam) {
        Review review = new Review(reviewParam);

        // Check if the user can review the event
        if(this.getStatusTicket().equals(StatusTicket.USED)) {
            this.setReview(review);
        }

        throw new TicketException("The ticket was not used yet.");
    }
}
