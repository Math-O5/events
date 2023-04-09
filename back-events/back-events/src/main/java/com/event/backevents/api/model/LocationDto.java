package com.event.backevents.api.model;

import jakarta.validation.constraints.NotBlank;

public class LocationDto {

    private String formattedAddress;
    private Double lat;
    private Double lng;

}
