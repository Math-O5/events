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
}
