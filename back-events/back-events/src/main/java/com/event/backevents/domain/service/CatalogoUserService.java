package com.event.backevents.domain.service;

import com.event.backevents.common.googleGeoLocation.GeoLocationServiceImpl;
import com.event.backevents.domain.model.Location;
import com.event.backevents.domain.model.User;
import com.event.backevents.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CatalogoUserService {
    private final UserRepository userRepository;

    @Autowired
    private final GeoLocationServiceImpl geoLocationService;

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new Error("User não encontrado."));
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Transactional
    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional
    public User add(User user) {

        boolean nameAlreadyPicked = userRepository.findByName(user.getName())
                .stream()
                .anyMatch(PubliherCreated -> !PubliherCreated.equals(user));

        if(nameAlreadyPicked) {
            throw new Error("Esse nome  já está em uso");
        }

        // Call getGeoCoding to retrieve latitude and longitude
        Optional<Location> location = geoLocationService.getGeoCodingForLoc(user.getLocation());

        if(location.isEmpty())
            throw new Error("Location not found.");

        user.setLocation(location.get());

        return userRepository.save(user);
    }

//    @Transactional
//    public User addEvent(Long publisherId, Event event) {
//
//        User publisher = userRepository.findById(publisherId)
//                .orElseThrow(() -> new Error("User não encontrado."));
//
//       // publisher.getEventCollection().add(event);
//
//        return userRepository.save(publisher);
//    }

}
