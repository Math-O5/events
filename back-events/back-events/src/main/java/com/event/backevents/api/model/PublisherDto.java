package com.event.backevents.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PublisherDto {
    private Long id;
    private String name;

    private List<EventIdDto> eventCollection;
}
