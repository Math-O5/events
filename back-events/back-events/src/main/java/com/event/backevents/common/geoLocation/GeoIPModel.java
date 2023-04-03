package com.event.backevents.common.geoLocation;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeoIPModel {

    private String ipAddress;
    // private String device;
    private String city;
    // private String fullLocation;
    private Double latitude;
    private Double longitude;

}