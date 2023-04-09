package com.event.backevents.common.googleGeoLocation;


import com.event.backevents.domain.model.Location;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoCodingResult {
    @JsonProperty(value = "formatted_address")
    String formattedAddress;

    @JsonProperty(value= "geometry")
    Geometry geometry;

    @JsonProperty("place_id")
    String placeId;

    @Override
    public String toString() {
        return "GeoCodingResult [formattedAddress=" + formattedAddress + ", placeId=" + placeId + "]";
    }

}

