package com.event.backevents.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EventDto {
    private Long Id;
    private String name;
    private PublisherIdDto publisher;
    private List<EventEditionIdDto> eventEditionCollection;
}
