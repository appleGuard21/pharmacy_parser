package com.appleguard.pharmacy_parser;

import com.appleguard.pharmacy_parser.entity.Drug;
import com.appleguard.pharmacy_parser.parsers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

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
        String inputDrug = "анальгин";
        String city = "Москва";
        List<Drug> drugList = serdceParser.parse(inputDrug,city);
        assertThat(drugList).isNotNull();
        assertThat(drugList).isNotEmpty();
    }
    @Test
    void stolichkiTest(){
        String inputDrug = "анальгин";
        String city = "Москва";
        List<Drug> drugList = stolichkiParser.parse(inputDrug,city);
        assertThat(drugList).isNotNull();
        assertThat(drugList).isNotEmpty();
    }
    @Test
    void apteka366Test(){
        String inputDrug = "супрастин";
        String city = "Москва";
        List<Drug> drugList = apteka366Parser.parse(inputDrug,city);
        assertThat(drugList).isNotNull();
        assertThat(drugList).isNotEmpty();
    }
    @Test
    void stoletovTest(){
        String inputDrug = "анальгин";
        String city = "Москва";
        List<Drug> drugList = stoletovParser.parse(inputDrug,city);
        assertThat(drugList).isNotNull();
        assertThat(drugList).isNotEmpty();
    }
    @Test
    void gorzdravTest(){
        String inputDrug = "супрастин";
        String city = "Москва";
        List<Drug> drugList = gorzdravParser.parse(inputDrug,city);
        assertThat(drugList).isNotNull();
        assertThat(drugList).isNotEmpty();
    }

}
