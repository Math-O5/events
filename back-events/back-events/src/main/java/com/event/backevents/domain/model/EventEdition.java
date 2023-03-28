package com.event.backevents.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventEdition extends BaseEntity {

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String descricao;
    private Float rate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Event event;

    @Override
    public String toString() {
        return "EventEdition{" +
                "name='" + name + '\'' +
                ", desc='" + descricao + '\'' +
                ", rate=" + rate +
                '}';
    }
}
