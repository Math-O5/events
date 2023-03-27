package com.event.backevents.common;

import com.event.backevents.api.model.EventDto;
import com.event.backevents.domain.model.Event;
import com.event.backevents.domain.model.Publisher;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

//        modelMapper.createTypeMap(Event.class, EventDto.class).addMapping(src -> src.getPublisher().getId(),
//                (dest, value) -> dest.setPublisher(new Publisher(id: value)));

//        modelMapper.addMappings(new PropertyMap<Event, EventDto>() {
//            @Override
//            protected void configure() {
//                // Tells ModelMapper to NOT populate company
//                skip(destination.getPublisher());
//            }
//        });

        modelMapper.getConfiguration().setPreferNestedProperties(false);


        return modelMapper;

    }
}


