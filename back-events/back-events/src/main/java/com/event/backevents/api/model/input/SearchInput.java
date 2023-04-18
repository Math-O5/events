package com.event.backevents.api.model.input;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
public class SearchInput {
    public Long userId;
    private OffsetDateTime initDate;
    private OffsetDateTime endDate;
    Float distance;
    int page;
    int size;
    KmOrMiles kmOrMiles;
}
