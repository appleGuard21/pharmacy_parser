package com.appleguard.pharmacy_parser;

import com.appleguard.pharmacy_parser.additionalTools.City;
import com.appleguard.pharmacy_parser.entity.Drug;
import com.appleguard.pharmacy_parser.parsers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.appleguard.pharmacy_parser.additionalTools.City.MOSCOW;
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
        String inputDrug = "��������";
        List<Drug> drugList = serdceParser.parse(inputDrug, MOSCOW);
        assertThat(drugList).isNotNull();
        assertThat(drugList).isNotEmpty();
    }
    @Test
    void stolichkiTest(){
        String inputDrug = "��������";
        List<Drug> drugList = stolichkiParser.parse(inputDrug,MOSCOW);
        assertThat(drugList).isNotNull();
        assertThat(drugList).isNotEmpty();
    }
    @Test
    void apteka366Test(){
        String inputDrug = "���������";
        List<Drug> drugList = apteka366Parser.parse(inputDrug,MOSCOW);
        assertThat(drugList).isNotNull();
        assertThat(drugList).isNotEmpty();
    }
    @Test
    void stoletovTest(){
        String inputDrug = "��������";
        List<Drug> drugList = stoletovParser.parse(inputDrug,MOSCOW);
        assertThat(drugList).isNotNull();
        assertThat(drugList).isNotEmpty();
    }
    @Test
    void gorzdravTest(){
        String inputDrug = "���������";
        List<Drug> drugList = gorzdravParser.parse(inputDrug,MOSCOW);
        assertThat(drugList).isNotNull();
        assertThat(drugList).isNotEmpty();
    }

}
