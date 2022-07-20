package com.appleguard.pharmacy_parser;

import com.appleguard.pharmacy_parser.additionalTools.City;
import com.appleguard.pharmacy_parser.entity.Drug;
import com.appleguard.pharmacy_parser.exceptions.NoSuchAnaloguesException;
import com.appleguard.pharmacy_parser.exceptions.NoSuchCityException;
import com.appleguard.pharmacy_parser.service.ParcedDrugs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MyRestController {

    @Autowired
    ParcedDrugs parcedDrugs;

    List<Drug> parcedDrugsList = new ArrayList<>();

    @GetMapping("/{city}/{inputDrug}")
    public List<Drug> drugList(@PathVariable String inputDrug, @PathVariable String city) throws IOException {
        try {
            parcedDrugsList = parcedDrugs.getParsedDrugs(inputDrug, City.valueOf(city));
        } catch (IllegalArgumentException e) {
            throw new NoSuchCityException();
        }
        return parcedDrugsList;
    }

    @GetMapping("/analogue/{city}/{number}")
    public List<Drug> analogueDrugList(@PathVariable int number, @PathVariable String city) throws IOException {
        if(!parcedDrugsList.isEmpty() && number>-1 && number< parcedDrugsList.size()){
            Drug drug = parcedDrugsList.get(number);
            String inputDrug = drug.getOriginalName();
            if(inputDrug!=null){
                return parcedDrugs.getParsedDrugs(inputDrug, City.valueOf(city));
            }else throw new NoSuchAnaloguesException();
            } else throw new NoSuchAnaloguesException();
        }

    }

