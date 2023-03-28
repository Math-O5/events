package com.event.backevents.common.geoLocation;

//import ua_parser.Client;
//import ua_parser.Parser;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

@Service
@AllArgsConstructor
public class GeoIPLocationServiceImpl implements GeoIPLocationService {

//    @Autowired
//    private DatabaseReader databaseReader;
    private static final String UNKNOWN = "UNKNOWN";

//    private String getDeviceDetails(String userAgent) throws IOException {
//        String deviceDetails = UNKNOWN;
//
//        Parser parser = new Parser();
//
//        Client client = parser.parse(userAgent);
//        if (client != null) {
//            deviceDetails = client.userAgent.family + " " + client.userAgent.major + "." + client.userAgent.minor +
//                    " - " + client.os.family + " " + client.os.major + "." + client.os.minor;
//        }
//
//        return deviceDetails;
//    }

    @Override
    public GeoIPModel getLocation(String ip, HttpServletRequest http) throws IOException, GeoIp2Exception {
        InetAddress ipAddress = InetAddress.getByName(ip);
        File database = new File("src/main/resources/maxmind/GeoLite2-City.mmdb");
        DatabaseReader dbReader = new DatabaseReader.Builder(database).build();

        CityResponse response =  dbReader.city(ipAddress);

        String cityName = response.getCity().getName();
        Double latitude = (response.getLocation() != null) ? response.getLocation().getLatitude() : 0;
        Double longitude = (response.getLocation() != null) ? response.getLocation().getLongitude() : 0;


        return new GeoIPModel(ip, cityName, latitude, longitude);
    }
}
