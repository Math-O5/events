package com.event.backevents.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Publisher {
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id Long id;
    private String name;
    private String cpf;
    private String cnpj;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    private List<Event> eventCollection = new ArrayList<>();

    // @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    // private Set<Event> eventOffCollection = new HashSet<>();

    public Event createNewEvent(Event event) {
        Event newEvent = new Event();
        newEvent.setName(event.getName());
        newEvent.setCodeName(event.getCodeName());
        newEvent.setPublisher(this);
        this.getEventCollection().add(newEvent);

        // default values
        newEvent.setIsActive(true);
        newEvent.setRate(0f);

        return newEvent;
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", eventCollection=" + eventCollection.toString() +
                '}';
    }
}
