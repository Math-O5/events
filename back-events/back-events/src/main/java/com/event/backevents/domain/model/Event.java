package com.event.backevents.domain.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Event extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String codeName;
    private String descricao;
    private Boolean isActive;
    private Float rate;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventEdition> eventEditionCollection = new ArrayList<>();

    public EventEdition createNewEventEdition(Event event, EventEdition eventEdition) {
        EventEdition newEventEdition = new EventEdition();
        newEventEdition.setName(eventEdition.getName());
        newEventEdition.setDescricao(eventEdition.getDescricao());
        newEventEdition.setLocation(eventEdition.getLocation());
        newEventEdition.setQtdTickets(eventEdition.getQtdTickets());
        newEventEdition.setEvent(this);
        this.getEventEditionCollection().add(newEventEdition);

        System.out.println(this.getEventEditionCollection().toString());

        // default values
        newEventEdition.setQtdLockedTickets(0);
        newEventEdition.setRate(0f);

        return newEventEdition;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", codeName='" + codeName + '\'' +
                ", user=" + user.toString() +
                '}';
    }
}
