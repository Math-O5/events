package com.event.backevents.domain.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor @AllArgsConstructor @Getter @Setter
@Entity
public class Event {
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.AUTO)
    private @Id Long id;
    private String name;
    private String codeName;
    //@ManyToOne
    //private User user;

}
