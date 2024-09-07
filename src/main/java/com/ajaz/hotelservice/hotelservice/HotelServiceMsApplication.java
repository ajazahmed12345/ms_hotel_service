package com.ajaz.hotelservice.hotelservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HotelServiceMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotelServiceMsApplication.class, args);
    }

}
