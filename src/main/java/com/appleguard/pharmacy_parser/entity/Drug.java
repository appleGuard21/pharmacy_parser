package com.appleguard.pharmacy_parser.entity;

import lombok.Data;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.stereotype.Component;

@Component
@Data
@RedisHash("Drug")
public class Drug {
     int id;
     String fullName;
     double price;
     String drugStore;
     String originalName;

}

