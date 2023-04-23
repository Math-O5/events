package com.event.backevents.domain.service;

import com.event.backevents.api.model.input.KmOrMiles;
import com.event.backevents.api.model.input.SearchInput;
import com.event.backevents.domain.model.EventEdition;
import com.event.backevents.domain.model.User;
import com.event.backevents.domain.repository.EventEditionRepository;
import com.event.backevents.domain.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BuscaEventEditionService {

    @Autowired
    private EventEditionRepository eventEditionRepository;

    @Autowired
    private UserRepository userRepository;

    public Optional<Page<EventEdition>> eventNearByUser(SearchInput searchInput) {

        Long userId = searchInput.userId;
        int page = searchInput.getPage();
        int size = searchInput.getSize();
        Float distance = searchInput.getDistance();
        KmOrMiles kmOrMiles = searchInput.getKmOrMiles();
        OffsetDateTime init = searchInput.getInitDate();

        if(init.isAfter(OffsetDateTime.now())) {
            throw new RuntimeException("The date is expired.");
        }

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

        return eventEditionRepository.findAllByLocationPagination(lat, lng, distance, kmOrMiles.toNumber(), init, pageRequest);
    }
}
