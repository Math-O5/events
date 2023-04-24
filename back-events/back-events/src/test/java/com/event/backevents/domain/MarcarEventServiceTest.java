package com.event.backevents.domain;


import com.event.backevents.domain.model.Event;
import com.event.backevents.domain.model.Location;
import com.event.backevents.domain.model.User;
import com.event.backevents.domain.repository.EventRepository;
import com.event.backevents.domain.repository.UserRepository;
import com.event.backevents.domain.service.CatalogoUserService;
import com.event.backevents.domain.service.MarcarEventService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MarcarEventServiceTest {

    @Autowired
    private MarcarEventService marcarEventService;

    @Autowired
    private CatalogoUserService catalogoUserService;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testMarcarEvent() {

        // Create a new user
        User user = new User();
        user.setName("John Doe");
        user.setLocation(new Location());
        userRepository.save(user);

        // Create a new event
        Event event = new Event();
        event.setName("Test Event");
        event.setDescricao("This is a test event");
        event.setUser(user);

        // Call the marcar method to mark the event for the user
        Event markedEvent = marcarEventService.marcar(event);

        // Verify that the marked event is not null and has been saved
        assertNotNull(markedEvent);
        assertNotNull(markedEvent.getId());
        assertEquals(event.getName(), markedEvent.getName());
        assertEquals(event.getDescricao(), markedEvent.getDescricao());

        // Verify that the event has been added to the user's list of events
        User updatedUser = catalogoUserService.findById(user.getId());
        assertNotNull(updatedUser.getEventCollection());
        assertEquals(1, updatedUser.getEventCollection().size());
        assertEquals(markedEvent.getId(), updatedUser.getEventCollection().get(0).getId());
    }

    @org.junit.Test(expected = RuntimeException.class)
    public void testMarcarEventWithNonexistentUser() {

        // Create a new event with a user that doesn't exist
        User user = new User();
        user.setId(123L);
        Event event = new Event();
        event.setName("Test Event");
        event.setDescricao("This is a test event");
        event.setUser(user);

        // Call the marcar method, which should throw a RuntimeException
        marcarEventService.marcar(event);
    }
}
