package com.event.backevents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
// import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"com.event.backevents"})
public class BackEventsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackEventsApplication.class, args);
	}

}
