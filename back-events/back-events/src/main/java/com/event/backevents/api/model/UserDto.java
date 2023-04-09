package com.event.backevents.api.model;

import com.event.backevents.domain.model.Location;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String name;
    private List<EventIdDto> eventCollection;
    private Location location;
}
