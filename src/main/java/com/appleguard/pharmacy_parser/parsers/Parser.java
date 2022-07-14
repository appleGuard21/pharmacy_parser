package com.appleguard.pharmacy_parser.parsers;


import com.appleguard.pharmacy_parser.entity.Drug;
import java.util.List;

public interface Parser {
    List<Drug> parse(String inputDrug, String city);
}
