package com.event.backevents.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Data
public class Review extends BaseEntity {

    @OneToOne
    Ticket ticket;

    @Column
    private Float rate;

    @Column
    private String comment;

    public Review(Review review) {
        this.setRate(review.getRate());
        this.setComment(review.getComment());
    }
}
