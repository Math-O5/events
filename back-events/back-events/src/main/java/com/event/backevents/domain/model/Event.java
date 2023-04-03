package com.event.backevents.domain.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Publisher publisher;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<EventEdition> eventEditionCollection = new ArrayList<>();

    public EventEdition createNewEventEdition(Event event, EventEdition eventEdition) {
        EventEdition newEventEdition = new EventEdition();
        newEventEdition.setName(eventEdition.getName());
        newEventEdition.setDescricao(eventEdition.getDescricao());
        newEventEdition.setLocation(eventEdition.getLocation());
        newEventEdition.setEvent(this);
        this.getEventEditionCollection().add(newEventEdition);

        System.out.println(this.getEventEditionCollection().toString());

        // default values
        newEventEdition.setRate(0f);

        return newEventEdition;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", codeName='" + codeName + '\'' +
                ", publisher=" + publisher.toString() +
                '}';
    }
}
