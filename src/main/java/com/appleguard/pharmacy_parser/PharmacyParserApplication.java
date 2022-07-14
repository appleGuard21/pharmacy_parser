package com.appleguard.pharmacy_parser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class PharmacyParserApplication {

    public static void main(String[] args) {
        SpringApplication.run(PharmacyParserApplication.class, args);
    }

}
