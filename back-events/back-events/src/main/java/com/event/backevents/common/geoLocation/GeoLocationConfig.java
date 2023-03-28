package com.event.backevents.common.geoLocation;

import com.maxmind.db.Reader;
import com.maxmind.geoip2.DatabaseReader;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Configuration
public class GeoLocationConfig {

//    @Autowired
    public static DatabaseReader reader;
    private final ResourceLoader resourceLoader;

    public GeoLocationConfig(ResourceLoader resourceLoader) { this.resourceLoader = resourceLoader; }

    //@Bean
    public DatabaseReader databaseReader() {
        try {
            log.info("GeoLocationConfig: Trying to load GeoLite2-Country database...");

            Resource resource = resourceLoader.getResource("classpath: maxmind/GeoLite2-City.mmdb");
            InputStream dbAsStrem = resource.getInputStream();

            return this.reader = new DatabaseReader
                                        .Builder(dbAsStrem)
                                        .fileMode(Reader.FileMode.MEMORY)
                                        .build();

        } catch(IOException | NullPointerException e) {
            log.error("Database reader could not be initialized.");
            return null;
        }
    }

}
