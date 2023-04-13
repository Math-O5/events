package com.event.backevents.api.assembler;

import com.event.backevents.api.model.EventDto;
import com.event.backevents.api.model.EventEditionDto;
import com.event.backevents.api.model.input.EventInput;
import com.event.backevents.domain.model.Event;
import com.event.backevents.domain.model.EventEdition;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class EventEditionAssembler {

    private ModelMapper modelMapper;

    public EventEditionDto toModel(EventEdition event) {

        modelMapper = new ModelMapper();

        TypeMap<EventEdition, EventEditionDto> propertyMapper = modelMapper.createTypeMap(EventEdition.class, EventEditionDto.class);

        propertyMapper.addMappings(
                mapper -> mapper.map(src -> src.getLocation(), EventEditionDto::setLocation)
        );

        return modelMapper.map(event, EventEditionDto.class);

    }

    public List<EventEditionDto> toCollectionModel(List<EventEdition> events) {
        return events.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public EventEdition toEntity(EventInput eventInput) {
        return modelMapper.map(eventInput, EventEdition.class);
    }
}
