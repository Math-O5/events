package com.event.backevents.domain.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@NoArgsConstructor @Getter @Setter
@Entity
public class User {
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Id Long id;
    private String firstName;
    private String lastNAme;

    // @Getter(lazy = true)
    // must be final and static, apply in the bootstrap layer!
    @OneToMany
    @JoinColumn(name = "user_id")
    private Set<Event> eventColletion = new HashSet<>();

    public User(Long id, String firstName, String lastNAme, Set<Event> eventColletion) {
        this.id = id;
        this.firstName = firstName;
        this.lastNAme = lastNAme;
        this.eventColletion = eventColletion;
    }
}

