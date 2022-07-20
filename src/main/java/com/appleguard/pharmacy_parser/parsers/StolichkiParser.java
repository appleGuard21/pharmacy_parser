package com.appleguard.pharmacy_parser.parsers;

import com.appleguard.pharmacy_parser.additionalTools.City;
import com.appleguard.pharmacy_parser.additionalTools.ParsersTools;
import com.appleguard.pharmacy_parser.additionalTools.Translator;
import com.appleguard.pharmacy_parser.entity.Drug;
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

import static com.appleguard.pharmacy_parser.additionalTools.CookieCities.*;

@Component
public class StolichkiParser implements Parser {
    @Autowired
    Translator translator;
    @Autowired
    ParsersTools parsersTools;

    @Override
    public List<Drug> parse(String inputDrug, City city){
            List<Drug> drugsList = new ArrayList<>();
            WebClient webClient = parsersTools.getWebClient();
            inputDrug = translator.translate(inputDrug);
        try {
            HtmlPage page = webClient.getPage("https://stolichki.ru");
            switch (city) {
                case MOSCOW -> {
                    Cookie myCookie = new Cookie(".stolichki.ru","cityId", MOSCOW_STOLICHKI.getCity());
                    parsersTools.setCityByCookie(webClient,"cityId",myCookie);
                }
                case ST_PETERSBURG -> {
                    Cookie myCookie = new Cookie(".stolichki.ru","cityId", PITER_STOLICHKI.getCity());
                    parsersTools.setCityByCookie(webClient,"cityId",myCookie);
                }
                default -> {
                    return drugsList;
                }
            }
            page = webClient.getPage("https://stolichki.ru/search?name="+inputDrug);
            HtmlDivision div = (HtmlDivision) page.getFirstByXPath("//*[@id=\"catalog-list\"]");
            if (div!=null) {
                for (int i = 1; i <= div.getChildElementCount(); i++) {
                    try {
                    DomElement domName = div.getFirstByXPath("//*[@id=\"catalog-list\"]/div[" + i + "]/div[2]/p");
                    DomElement domPrice = div.getFirstByXPath("//*[@id=\"catalog-list\"]/div[" + i + "]/div[2]/div/div/p");
                    if (domName != null && domPrice != null) {
                        drugsList.add(parsersTools.newDrug(
                                parsersTools.editedName(domName)
                                , parsersTools.editedPrice(domPrice), "Аптека столички",null));
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
