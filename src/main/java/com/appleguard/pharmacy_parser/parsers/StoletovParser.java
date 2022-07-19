package com.appleguard.pharmacy_parser.parsers;

import com.appleguard.pharmacy_parser.additionalTools.City;
import com.appleguard.pharmacy_parser.additionalTools.ParsersTools;
import com.appleguard.pharmacy_parser.additionalTools.Translator;
import com.appleguard.pharmacy_parser.entity.Drug;
import com.appleguard.pharmacy_parser.exceptions.NoSuchCityException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.appleguard.pharmacy_parser.additionalTools.City.*;

@Component
public class StoletovParser implements Parser{
    @Autowired
    ParsersTools tools;
    @Autowired
    Translator translator;
    @Override
    public List<Drug> parse(String inputDrug, City city) {
        List<Drug> drugsList = new ArrayList<>();
        WebClient webClient = tools.getWebClient();
        inputDrug = translator.translate(inputDrug);
        try {
            HtmlPage page = webClient.getPage("https://stoletov.ru/");
            switch (city) {
                case MOSCOW -> {
                    Cookie myCookie = new Cookie("stoletov.ru","SELECTED_CITY", MOSCOW_STOLETOV.getCity());
                    tools.setCityByCookie(webClient,"cityId",myCookie);
                }
                case PITER -> {
                    Cookie myCookie = new Cookie("stoletov.ru","SELECTED_CITY", PITER_STOLETOV.getCity());
                    tools.setCityByCookie(webClient,"SELECTED_CITY",myCookie);
                }
                default -> {
                    return drugsList;
                }
            }
            page = webClient.getPage("https://stoletov.ru/catalog/?q="+inputDrug);
            HtmlDivision div = (HtmlDivision) page.getFirstByXPath("/html/body/div[5]/main/div/div[3]/div/div[2]/div[2]/div[1]");
            if (div!=null) {
                for (int i = 1; i <= div.getChildElementCount(); i++) {
                    try {
                        DomElement itemName = div.getFirstByXPath
                                ("/html/body/div[5]/main/div/div[3]/div/div[2]/div[2]/div[1]/div["+i+"]/div/div/div[2]/div/a");
                        DomElement itemPrice = div.getFirstByXPath
                                ("/html/body/div[5]/main/div/div[3]/div/div[2]/div[2]/div[1]/div["+i+"]/div/div/div[2]/div/div[2]/span");
                        DomElement itemOriginalName = div
                                .getFirstByXPath("/html/body/div[5]/main/div/div[3]/div/div[2]/div[2]/div[1]/div[" + i + "]/div/div/div[2]/div/div[1]");
                        if (itemName != null && itemPrice != null) {
                            String name = tools.editedName(itemName);
                            double price = tools.editedPrice(itemPrice);
                            String originalName;
                            if (itemOriginalName!=null) {
                                originalName = tools.editedOriginalName(itemOriginalName);
                            }else originalName = null;
                            Drug drug = tools.newDrug(name, price, "Доктор Столетов",originalName);
                            drugsList.add(drug);
                        }
                    }catch (NullPointerException e){
                        System.out.println(e.getMessage());
                        return drugsList;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return drugsList;
    }
}


