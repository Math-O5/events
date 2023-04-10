package com.event.backevents.domain.service;

import com.event.backevents.domain.model.EventEdition;
import com.event.backevents.domain.model.User;
import com.event.backevents.domain.repository.EventEditionRepository;
import com.event.backevents.domain.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class BuscaEventEditionService {
    private EventEditionRepository eventEditionRepository;
    private UserRepository userRepository;

    public Optional<Page<EventEdition>> eventNearByUser(Long userId, Integer distance, int page, int size) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty())
            return Optional.empty();

        Double lat = user.get().getLocation().getLat();
        Double lng = user.get().getLocation().getLng();
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "name");

        return eventEditionRepository.findAllByLocationPagination(lat, lng, distance, pageRequest);
    }
}
