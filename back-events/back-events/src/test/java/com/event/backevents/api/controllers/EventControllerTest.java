package com.event.backevents.api.controllers;

import com.event.backevents.api.assembler.EventAssembler;
import com.event.backevents.api.exceptionhandler.ResourceNotFoundException;
import com.event.backevents.domain.model.Event;
import com.event.backevents.domain.model.User;
import com.event.backevents.domain.repository.EventRepository;
import com.event.backevents.domain.repository.UserRepository;
import com.event.backevents.domain.service.CatalogoEventService;
import com.event.backevents.domain.service.MarcarEventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
//@ExtendWith(EventController.class)
//@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @MockBean
    private CatalogoEventService catalogoEventService;

    @MockBean
    private MarcarEventService marcarEventService;

    @Autowired
    private EventAssembler eventAssembler;

    private EventController eventController;

    @Before
    public void setUp() {
        eventController = new EventController();
        eventController.setCatalogoEventService(catalogoEventService);
        eventController.setEventAssembler(eventAssembler);
        eventController.setEventRepository(eventRepository);
        eventController.setMarcarEventService(marcarEventService);
        eventController.setUserRepository(userRepository);
    }


//    @InjectMocks
//    public EventController eventController;

    @Test
    public void testListEvents() throws Exception {

        // Create some test events
        User user = new User();
        user.setName("John Doe");
        //user.setEmail("john.doe@example.com");
        user = userRepository.save(user);

        Event event1 = new Event();
        event1.setName("Test Event 1");
        event1.setDescricao("This is a test event 1");
        event1.setUser(user);
        eventRepository.save(event1);

        Event event2 = new Event();
        event2.setName("Test Event 2");
        event2.setDescricao("This is a test event 2");
        event2.setUser(user);
        eventRepository.save(event2);

        // Send a GET request to the /events endpoint
        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", Matchers.is(event1.getId().intValue())))
                .andExpect(jsonPath("$[0].name", Matchers.is(event1.getName())))
                .andExpect(jsonPath("$[0].descricao", Matchers.is(event1.getDescricao())))
                .andExpect(jsonPath("$[1].id", Matchers.is(event2.getId().intValue())))
                .andExpect(jsonPath("$[1].name", Matchers.is(event2.getName())))
                .andExpect(jsonPath("$[1].descricao", Matchers.is(event2.getDescricao())));
    }

    @org.junit.jupiter.api.Test
    public void testAddEvent() throws Exception {

        // Create some test events
        User user = new User();
//        user.setId(1L);
        user.setName("John Doe");
        //user.setEmail("john.doe@example.com");
        user = userRepository.save(user);


        Event event = new Event();
        event.setName("Test Event 1");
        event.setDescricao("This is a test event 1");
        event.setRate(0f);
        event.setIsActive(true);
        event.setUser(user);
//        event = eventRepository.save(event);

        // Mock the MarcarEventService to return the same event that was passed in
        Mockito.when(marcarEventService.marcar(event)).thenReturn(event);

        ObjectMapper objectMapper = new ObjectMapper();
        String source = objectMapper.writeValueAsString(event);

        // Send a POST request to the /events endpoint with the event data in the request body
        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(source))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", Matchers.is(event.getName())))
                .andExpect(jsonPath("$.descricao", Matchers.is(event.getDescricao()))
                );

        // Verify that the event was saved to the database
        List<Event> events = eventRepository.findAll();
        assertEquals(1, events.size());
        assertEquals(event.getName(), events.get(0).getName());
        assertEquals(event.getDescricao(), events.get(0).getDescricao());
    }

    @org.junit.jupiter.api.Test
    public void testAddEventWithNonexistentUser() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        // Create a new event with a user that doesn't exist
        User user = new User();
        user.setId(123L);
        Event event = new Event();
        event.setName("Test Event");
        event.setDescricao("This is a test event");
        event.setUser(user);

        // Mock the CatalogoEventService to throw a ResourceNotFoundException
        doThrow(ResourceNotFoundException.class).when(catalogoEventService).save(event);

        // Send a POST request to the /events endpoint with the event data in the request body
        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isNotFound());

        // Verify that the event was not saved to the database
        List<Event> events = eventRepository.findAll();
        assertEquals(0, events.size());
    }

    @org.junit.Test
    public void testControllerInitialization() {

    }
}




//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;

//import com.event.backevents.api.assembler.EventAssembler;
//import com.event.backevents.api.controllers.EventController;
//import com.event.backevents.api.model.EventDto;
//import com.event.backevents.api.model.UserDto;
//import com.event.backevents.api.model.UserIdDto;
//import com.event.backevents.domain.model.Event;
//import com.event.backevents.domain.model.EventEdition;
//import com.event.backevents.domain.model.User;
//import com.event.backevents.domain.repository.EventEditionRepository;
//import com.event.backevents.domain.repository.EventRepository;
//import com.event.backevents.domain.repository.TicketRepository;
//import com.event.backevents.domain.repository.UserRepository;
//import com.event.backevents.domain.service.MarcarEventService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.time.OffsetDateTime;

//@ExtendWith(MockitoExtension.class)
//@ExtendWith(SpringExtension.class)
//@AutoConfigureMockMvc
//@WebMvcTest(EventController.class)
//public class EventControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    @Autowired
//    private UserRepository userRepository;
//
//    @MockBean
//    private EventRepository eventRepository;
//
//    @MockBean
//    private EventEditionRepository eventEditionRepository;
//
//    @MockBean
//    private TicketRepository ticketRepository;
//
//    @Mock
////    @Autowired
//    private MarcarEventService marcarEventService;
//
//    @Autowired
//    private EventAssembler eventAssembler;
//
////    @InjectMocks
////    private EventController eventController;
//
//    @Test
//    public void testAddEvent() throws Exception {
//        ObjectMapper objectMapper = new ObjectMapper();
//        final MockMvc mockMvc = MockMvcBuilders.standaloneSetup(EventController.class).build();
//
//        // Create a mock user
//        User user = new User();
//        user.setId(1L);
//
//        // Create a mock event
//        Event event = new Event();
//        event.setUser(user);
//        event.setName("Test Event");
//        event.setIsActive(true);
//
//        // Create a mock scheduled event
//        Event scheduledEvent = new Event();
//        scheduledEvent.setId(1L);
//        scheduledEvent.setUser(user);
//        scheduledEvent.setName("Test Event");
//        scheduledEvent.setIsActive(true);
//        scheduledEvent.setDescricao("Test");
//        scheduledEvent.setCodeName("Test");
//        scheduledEvent.setRate(0f);
//
//        // Configure mockito behavior for userRepository
//        when(userRepository.existsById(user.getId())).thenReturn(true);
//
////        when(eventRepository.)
//        // Configure mockito behavior for marcarEventService
//        when(marcarEventService.marcar(any(Event.class))).thenReturn(scheduledEvent);
//
//        EventDto eventDto = new EventDto();
//        UserIdDto userIdDto = new UserIdDto();
//        userIdDto.setId(1L);
//
//        eventDto.setUser(userIdDto);
//        eventDto.setId(1L);
//        eventDto.setName("Test Event");
//
//        // Configure mockito behavior for eventAssembler
////        when(eventAssembler.toModel(scheduledEvent)).thenReturn(eventDto);
//
//        // Send a mock HTTP POST request to add the event
//        mockMvc.perform(MockMvcRequestBuilders.post("/events")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(eventDto))
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isCreated());
////                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
////                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Event"))
////                .andExpect(MockMvcResultMatchers.jsonPath("$.codeName").value("Test"));
//    }
//}

