package com.event.backevents.api.model;

import com.event.backevents.domain.model.Location;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class EventEditionDto {
    private Long id;
    private String name;
    private String descricao;
    private Integer qtdTickets;
    private Float rate;
    private Location location;
    private OffsetDateTime initDate;
    private OffsetDateTime endDate;
}
