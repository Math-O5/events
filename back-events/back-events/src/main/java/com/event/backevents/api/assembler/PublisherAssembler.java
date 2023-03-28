package com.event.backevents.api.assembler;

import com.event.backevents.api.model.EventDto;
import com.event.backevents.api.model.LocationDto;
import com.event.backevents.api.model.PublisherDto;
import com.event.backevents.api.model.input.EventInput;
import com.event.backevents.api.model.input.PublisherInput;
import com.event.backevents.domain.model.Event;
import com.event.backevents.domain.model.Publisher;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Component
public class PublisherAssembler {
    private ModelMapper modelMapper;

    public PublisherDto toModel(Publisher publisher) {
        return modelMapper.map(publisher, PublisherDto.class);
    }

    public List<PublisherDto> toCollectionModel(List<Publisher> publishers) {
        return publishers.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public PublisherDto toEntity(PublisherInput publisherInput) {
        return modelMapper.map(publisherInput, PublisherDto.class);
    }
}
