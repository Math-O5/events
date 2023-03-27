package com.event.backevents.domain.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
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
