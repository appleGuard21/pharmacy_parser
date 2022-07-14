package com.appleguard.pharmacy_parser.service;


import com.appleguard.pharmacy_parser.entity.Drug;
import java.io.IOException;
import java.util.List;


public interface ParcedDrugs {
    List<Drug> getParsedDrugs(String inputDrug, String city) throws IOException;
}
