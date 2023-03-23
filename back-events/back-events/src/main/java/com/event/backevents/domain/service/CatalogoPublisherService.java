package com.event.backevents.domain.service;

import com.event.backevents.domain.model.Publisher;
import com.event.backevents.domain.repository.PublisherRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CatalogoPublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

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
    public Publisher save(Publisher publisher) {

        return publisherRepository.save(publisher);
    }

}
