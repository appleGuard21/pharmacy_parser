package com.appleguard.pharmacy_parser.service;

import com.appleguard.pharmacy_parser.additionalTools.City;
import com.appleguard.pharmacy_parser.entity.Drug;
import com.appleguard.pharmacy_parser.parsers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class ParcedDrugsImpl implements ParcedDrugs {
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
    @Override
    @Cacheable(key = "{#inputDrug,#city}", value = "Drug")
        public List<Drug> getParsedDrugs(String inputDrug, City city) {
            List<Drug> allDrugs = new ArrayList<>();
            List<List<Drug>> allLists = new ArrayList<>();
            List<Runnable> runnableList = new ArrayList<>();
        List<Parser>parsersList = new ArrayList<>();
        parsersList.add(serdceParser);
        parsersList.add(stoletovParser);
        parsersList.add(stolichkiParser);
        parsersList.add(apteka366Parser);
        parsersList.add(gorzdravParser);
        ExecutorService executorService = Executors.newFixedThreadPool(parsersList.size());
        for(Parser p:parsersList){
            Runnable r = ()-> addToList(allLists, p.parse(inputDrug, city));
            runnableList.add(r);
        }

        try {
            for (Runnable r : runnableList) {
                executorService.execute(r);
            }
        }finally {
            executorService.shutdown();
        }
        try {
            executorService.awaitTermination(15,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        for (List<Drug> allList : allLists) {
            if (!allList.isEmpty()) {
                allDrugs.addAll(allList);
            }
        }
            if(!allDrugs.isEmpty()) {
                allDrugs.sort((x, y) -> (int) (x.getPrice() - y.getPrice()));
                for(int i =0;i<allDrugs.size();i++){
                    Drug drug = allDrugs.get(i);
                    drug.setId(i);
                }
            }
            return allDrugs;
        }
        public synchronized void addToList(List<List<Drug>> mainList, List<Drug> drugsList){
          mainList.add(drugsList);
        }
}
