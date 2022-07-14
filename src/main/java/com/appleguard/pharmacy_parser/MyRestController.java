package com.appleguard.pharmacy_parser;

import com.appleguard.pharmacy_parser.entity.Drug;
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
        long time = System.currentTimeMillis();
        parcedDrugsList = parcedDrugs.getParsedDrugs(inputDrug, city);
        System.out.println("time to parse: " + (System.currentTimeMillis() - time));
        return parcedDrugsList;
    }
    @GetMapping("/analogue/{city}/{number}")
    public List<Drug> analogueDrugList(@PathVariable int number, @PathVariable String city) throws IOException {
        if(!parcedDrugsList.isEmpty()){
            Drug drug = parcedDrugsList.get(number);
            String inputDrug = drug.getOriginalName();
            return parcedDrugs.getParsedDrugs(inputDrug, city);
        }else return null;

    }

}
