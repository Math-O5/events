package com.event.backevents.api.assembler;

import com.event.backevents.api.model.EventDto;
import com.event.backevents.api.model.input.EventInput;
import com.event.backevents.domain.model.Event;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Component
public class EventAssembler {
    private ModelMapper modelMapper;

    public EventDto toModel(Event event) {
        return modelMapper.map(event, EventDto.class);
    }

    public List<EventDto> toCollectionModel(List<Event> events) {
        return events.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public Event toEntity(EventInput eventInput) {
        return modelMapper.map(eventInput, Event.class);
    }
}
