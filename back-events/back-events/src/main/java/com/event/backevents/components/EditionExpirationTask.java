package com.event.backevents.components;

import com.event.backevents.common.googleGeoLocation.GeoLocationServiceImpl;
import com.event.backevents.domain.model.EventEdition;
import com.event.backevents.domain.model.Ticket;
import com.event.backevents.domain.repository.EventEditionRepository;
import com.event.backevents.domain.repository.TicketRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor @NoArgsConstructor
@Component
public class EditionExpirationTask {

    @Autowired
    private EventEditionRepository eventEditionRepository;

    @Autowired
    private TicketRepository ticketRepository;

    private static final Logger log = LoggerFactory.getLogger(EditionExpirationTask.class);

    @Scheduled(fixedRate = 360000) // check every hour
    @Transactional
    public void checkForExpiredItems() {

        log.info("[Scheduled] - [Expire Ticket] - Calling expiring all tickets: checkForExpiredItems()");

        OffsetDateTime currentDate = OffsetDateTime.now();

        List<EventEdition> editionList = eventEditionRepository.findExpiredEditions(currentDate).get();

        // update the status of the expired items
        editionList.stream().forEach(event -> {

            List<Ticket> tickets = event.getTicketList();

            List<Ticket> expiredItems = new ArrayList<>();

            tickets.stream().forEach(ticket -> {
                ticket.expireTicket();
                expiredItems.add(ticket);
            });
            
            event.setTicketList(expiredItems);

            ticketRepository.saveAll(expiredItems);
            eventEditionRepository.save(event);

        });

        log.info("[Scheduled] - [Expire Ticket] - Ending expiring all tickets: checkForExpiredItems()");
    }
}
