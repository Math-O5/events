package com.event.backevents.api.model;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Data
@RequiredArgsConstructor
public class EventDto {
    private Long id;
    private String name;
    private String descricao;
    private UserIdDto user;
    private List<EventEditionIdDto> eventEditionCollection;
}
