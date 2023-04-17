package com.event.backevents.domain.service;

import com.event.backevents.api.assembler.EventAssembler;
import com.event.backevents.api.controllers.EventController;
import com.event.backevents.api.exceptionhandler.ResourceNotFoundException;
import com.event.backevents.api.model.EventDto;
import com.event.backevents.common.googleGeoLocation.GeoLocationServiceImpl;
import com.event.backevents.domain.model.Event;
import com.event.backevents.domain.model.Location;
import com.event.backevents.domain.model.User;
import com.event.backevents.domain.repository.EventRepository;
import com.event.backevents.domain.repository.UserRepository;
import com.event.backevents.domain.service.CatalogoEventService;
import com.event.backevents.domain.service.CatalogoUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import groovyjarjarpicocli.CommandLine;
import io.restassured.config.ObjectMapperConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class CatalogoEventServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private CatalogoEventService catalogoEventService;

    @Mock
    private GeoLocationServiceImpl geoLocationService;

    Location location;
    User returnUser;
    Event event;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        location = Location.builder().number(0).state("Sao Paulo")
                .street("Vila").postalCode("0").city("Carapicuiba").build();

        returnUser = User.builder().location(location).name("Julius").cnpj("1727373").build();

        event = Event.builder().id(1L).user(returnUser).build();
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void save() {
        Event returnedEvent = Event.builder().id(1L).user(returnUser).build();

        when(eventRepository.save(returnedEvent)).thenReturn(returnedEvent);

        Event event = catalogoEventService.save(returnedEvent);

        assertNotNull(event);
    }
}