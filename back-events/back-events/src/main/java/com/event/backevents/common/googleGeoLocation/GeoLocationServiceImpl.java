package com.event.backevents.common.googleGeoLocation;

import com.event.backevents.api.exceptionhandler.ResourceNotFoundException;
import com.event.backevents.domain.model.Location;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

@Service
@Data
public class GeoLocationServiceImpl {
    private static final Logger log = LoggerFactory.getLogger(GeoLocationServiceImpl.class);
    private static final String GEOCODING_URI = "https://maps.googleapis.com/maps/api/geocode/json";

    @Autowired
    private Environment env;

    public Optional<Location> getGeoCodingForLoc(Location locationParam) {

        RestTemplate restTemplate = new RestTemplate();
        Optional<Location> location = Optional.empty();
        String address = locationParam.toLocationSearch();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(GEOCODING_URI).queryParam("address", address)
                .queryParam("key", env.getProperty("apiKey"));

        log.info("Calling geocoding api with: " + builder.toUriString());

        GeoCoding geoCoding = restTemplate.getForObject(builder.toUriString(), GeoCoding.class);
        log.info(geoCoding.toString());

        if (geoCoding.getGeoCodingResults().length == 0) {
            throw new ResourceNotFoundException("This address was not found.");
        }

        // Copy param location and result (lat, lng) for the final object
        location = Optional.of(locationParam);

        location.get().setLat(geoCoding.getGeoCodingResults()[0].geometry.getLocation().getLat());
        location.get().setLng(geoCoding.getGeoCodingResults()[0].geometry.getLocation().getLng());
        location.get().setFormattedAddress(geoCoding.getGeoCodingResults()[0].formattedAddress);

        return location;
    }
}
