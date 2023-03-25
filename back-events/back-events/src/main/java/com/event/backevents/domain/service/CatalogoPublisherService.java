package com.event.backevents.domain.service;

import com.event.backevents.domain.model.Event;
import com.event.backevents.domain.model.Publisher;
import com.event.backevents.domain.repository.PublisherRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CatalogoPublisherService {

    private final PublisherRepository publisherRepository;

    public Publisher findById(Long userId) {
        return publisherRepository.findById(userId)
                .orElseThrow(() -> new Error("Publisher não encontrado."));
    }

    public List<Publisher> findAll() {
        return publisherRepository.findAll();
    }

    @Transactional
    public void delete(Publisher user) {
        publisherRepository.delete(user);
    }

    @Transactional
    public void deleteById(Long userId) {
        publisherRepository.deleteById(userId);
    }

    @Transactional
    public Publisher add(Publisher publisher) {

        System.out.println(publisher);

        boolean nameAlreadyPicked = publisherRepository.findByName(publisher.getName())
                .stream()
                .anyMatch(PubliherCreated -> !PubliherCreated.equals(publisher));

        if(nameAlreadyPicked) {
            throw new Error("Esse nome  já está em uso");
        }

        return publisherRepository.save(publisher);
    }

//    @Transactional
//    public Publisher addEvent(Long publisherId, Event event) {
//
//        Publisher publisher = publisherRepository.findById(publisherId)
//                .orElseThrow(() -> new Error("Publisher não encontrado."));
//
//       // publisher.getEventCollection().add(event);
//
//        return publisherRepository.save(publisher);
//    }

}
