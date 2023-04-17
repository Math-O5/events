package com.event.backevents.api.controller;

import com.event.backevents.api.assembler.EventAssembler;
import com.event.backevents.api.controllers.EventController;
import com.event.backevents.api.exceptionhandler.ResourceNotFoundException;
import com.event.backevents.api.model.EventDto;
import com.event.backevents.domain.model.Event;
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

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class EventControllerTest {

    @Mock
    private CatalogoEventService catalogoEventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventAssembler eventAssembler;

    @InjectMocks
    private EventController eventController;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        catalogoEventService = new CatalogoEventService(eventRepository);
    }

    @Test
    public void retrieveOk_findEventById () throws Exception {
        User user = User.builder().id(1L).build();

        Event event =  Event.builder().id(1L).name("João").user(user).build();

        when(eventRepository.findById(1L)).thenReturn(Optional.ofNullable(event));

        final MockMvc mvcMock = MockMvcBuilders.standaloneSetup(this.eventController).build();

        final MvcResult mvcResult = mvcMock
                .perform(get("/events").requestAttr("/{eventId}", 1L))
                .andExpect(status().isOk())
                .andReturn();

        final ObjectMapper objectMapper = new ObjectMapper();
        final EventDto eventDtoResult = objectMapper.readValue(
                mvcResult.getResponse().getContentAsString(),
                EventDto.class
        );

        assertEquals(event.getId(), eventDtoResult.getId());
        assertEquals(event.getName(), eventDtoResult.getName());
        assertEquals(event.getUser().getId(), eventDtoResult.getUser().getId());

        verify(eventRepository, times(1)).findAll();
    }

    @Test
    public void createOk_createEvent () throws Exception {

        Event event = new Event();

        event.setId(1L);
        event.setName("Aulas judô");
        event.setRate(0f);
        event.setCodeName("Judo");
        event.setIsActive(true);
        event.setDescricao("Aulas judô");

        User user = new User();
        user.setId(1L);

        // user.getEventCollection().add(event);
        event.setUser(user);

        userRepository.save(user);
        eventRepository.save(event);

        EventDto eventDto = eventAssembler.toModel(event);

        // when(eventRepository.findAll()).thenReturn(List.of(event));

        final ObjectMapper objectMapper = new ObjectMapper();
        final String json = objectMapper.writeValueAsString(event);

        final MockMvc mvcMock = MockMvcBuilders.standaloneSetup(this.eventController).build();

        final MvcResult mvcResult = mvcMock
                .perform(post("/events")
                        .requestAttr("eventId", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andExpect(status().isOk())
                .andReturn();

//        ModelMapper modelMapper = new ModelMapper();
//        final EventDto eventDtoResult = modelMapper.map(mvcResult.getResponse().getContentAsString(), EventDto.class);
//
//        assertEquals(eventDto.getId(), eventDtoResult.getId());
//        assertEquals(eventDto.getName(), eventDtoResult.getName());
//        assertEquals(eventDto.getUser().getId(), eventDtoResult.getUser().getId());

    }
}
