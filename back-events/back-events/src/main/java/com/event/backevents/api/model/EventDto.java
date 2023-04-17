package com.event.backevents.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EventDto {
    private Long id;
    private String name;
    private UserIdDto user;
    private List<EventEditionIdDto> eventEditionCollection;
}
