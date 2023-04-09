package com.event.backevents.common.googleGeoLocation;

import com.event.backevents.domain.model.Location;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class Geometry {
        Location location;
}
