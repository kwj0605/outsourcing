package com.sparta.outsourcing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@SpringBootApplication
public class OutsourcingApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutsourcingApplication.class, args);
    }

}
