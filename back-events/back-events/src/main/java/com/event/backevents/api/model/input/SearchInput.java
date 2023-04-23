package com.event.backevents.api.model.input;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@SuperBuilder
public class SearchInput {
    public Long userId;
    private OffsetDateTime initDate;
    Float distance;
    int page;
    int size;
    KmOrMiles kmOrMiles;
}
