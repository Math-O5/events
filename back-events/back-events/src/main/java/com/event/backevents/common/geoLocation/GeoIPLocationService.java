package com.event.backevents.common.geoLocation;

import com.maxmind.geoip2.exception.GeoIp2Exception;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface GeoIPLocationService {
    public GeoIPModel getLocation(String ip, HttpServletRequest http) throws IOException, GeoIp2Exception;
}
