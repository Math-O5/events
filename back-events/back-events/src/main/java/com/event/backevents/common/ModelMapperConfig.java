package com.event.backevents.common;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

//        modelMapper.createTypeMap(Event.class, EventDto.class).addMapping(src -> src.getUser().getId(),
//                (dest, value) -> dest.setUser(new User(id: value)));

//        modelMapper.addMappings(new PropertyMap<Event, EventDto>() {
//            @Override
//            protected void configure() {
//                // Tells ModelMapper to NOT populate company
//                skip(destination.getUser());
//            }
//        });

        modelMapper.getConfiguration().setPreferNestedProperties(false);


        return modelMapper;

    }
}


