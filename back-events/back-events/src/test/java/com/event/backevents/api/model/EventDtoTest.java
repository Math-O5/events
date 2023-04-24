package com.event.backevents.api.model;

import com.event.backevents.api.assembler.EventAssembler;
import com.event.backevents.domain.model.Event;
import com.event.backevents.domain.model.User;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.configuration.IMockitoConfiguration;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EventDtoTest {

    @Test
    void eventToeventDtoTest() {

        // Create a mock user
        User user = new User();
        user.setId(1L);

        // Create a mock scheduled event
        Event scheduledEvent = new Event();
        scheduledEvent.setId(1L);
        scheduledEvent.setUser(user);
        scheduledEvent.setName("Test Event");
        scheduledEvent.setIsActive(true);
        scheduledEvent.setDescricao("Test");
        scheduledEvent.setCodeName("Test");
        scheduledEvent.setRate(0f);

        EventAssembler eventAssembler = new EventAssembler(new ModelMapper());

        EventDto eventDto = eventAssembler.toModel(scheduledEvent);

        assertEquals(scheduledEvent.getId(), eventDto.getId());
        assertEquals(scheduledEvent.getName(), eventDto.getName());
        assertEquals(scheduledEvent.getEventEditionCollection(), eventDto.getEventEditionCollection());
    }

}