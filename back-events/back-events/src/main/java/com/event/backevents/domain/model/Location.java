package com.event.backevents.domain.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.awt.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

    private Double lat;
    private Double lng;

    public String toLocationSearch() {
        String concated = this.getStreet().strip() +
                '+' + this.getCity().strip() + '+'
                + this.getState();
        return concated.strip().replace(" ", "+");
    }
}
