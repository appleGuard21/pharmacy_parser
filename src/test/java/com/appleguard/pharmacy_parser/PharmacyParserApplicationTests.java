package com.appleguard.pharmacy_parser;

import com.appleguard.pharmacy_parser.parsers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PharmacyParserApplicationTests {

    @Autowired
    StolichkiParser stolichkiParser;
    @Autowired
    SerdceParser serdceParser;
    @Autowired
    Apteka366Parser apteka366Parser;
    @Autowired
    StoletovParser stoletovParser;
    @Autowired
    GorzdravParser gorzdravParser;
    @Test
    void serdceTest(){
        String inputDrug = "супрастин";
        String city1 = "Москва";
        System.out.println(serdceParser.parse(inputDrug,city1));

    }
}
