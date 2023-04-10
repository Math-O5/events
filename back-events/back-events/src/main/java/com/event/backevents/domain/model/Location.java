package com.event.backevents.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter @Setter
@Embeddable
public class Location {
    @NotBlank
    private String street;

    @NotBlank
    private Integer number;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @NotBlank
    private String postalCode;

    private String formattedAddress;

    private Point point;

    private Double lat;
    private Double lng;
}
