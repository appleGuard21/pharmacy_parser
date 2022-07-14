package com.appleguard.pharmacy_parser.entity;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Drug {
     int id;
     String fullName;
     double price;
     String drugStore;
     String originalName;

}

