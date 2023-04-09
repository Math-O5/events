package com.event.backevents.domain.service;


import com.event.backevents.domain.model.Event;
import com.event.backevents.domain.model.User;
import com.event.backevents.domain.repository.EventRepository;
import com.event.backevents.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MarcarEventService {

    private CatalogoUserService catalogoUserService;
    private EventRepository eventRepository;
    private UserRepository userRepository;

    @Transactional
    public Event marcar(Event event) {

        User user = catalogoUserService.findById(event.getUser().getId());

        if(user == null)
            throw new RuntimeException("User does not exist");

        return eventRepository.save(user.createNewEvent(event));
    }

}
