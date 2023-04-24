package com.event.backevents.domain.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor @NoArgsConstructor
@Data
@Table(name = "users")
@SuperBuilder
public class User extends BaseEntity {

    private String name;
    private String cpf;
    private String cnpj;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Event> eventCollection = new ArrayList<>();

    @Embedded
    Location location;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Ticket> ticketList  = new ArrayList<>();

    // alternative way using hashmap
    // @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    // private Set<Event> eventOffCollection = new HashSet<>();

    public Event createNewEvent(Event event) {
        Event newEvent = new Event();
        newEvent.setName(event.getName());
        newEvent.setCodeName(event.getCodeName());
        newEvent.setUser(this);
        newEvent.setDescricao(event.getDescricao());

        this.getEventCollection().add(newEvent);

        // default values
        newEvent.setIsActive(true);
        newEvent.setRate(0f);

        return newEvent;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cpf='" + cpf + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", eventCollection=" + eventCollection.toString() +
                '}';
    }
}
