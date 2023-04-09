package com.event.backevents.api.assembler;

import com.event.backevents.api.model.EventEditionDto;
import com.event.backevents.api.model.TicketDto;
import com.event.backevents.api.model.input.EventInput;
import com.event.backevents.api.model.input.TicketInput;
import com.event.backevents.domain.model.EventEdition;
import com.event.backevents.domain.model.Ticket;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class TicketAssembler {
    private ModelMapper modelMapper;

    public TicketDto toModel(Ticket ticket) {
        return modelMapper.map(ticket, TicketDto.class);
    }

    public List<TicketDto> toCollectionModel(List<Ticket> tickets) {
        return tickets.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    public Ticket toEntity(TicketInput ticketInput) {
        return modelMapper.map(ticketInput, Ticket.class);
    }
}
