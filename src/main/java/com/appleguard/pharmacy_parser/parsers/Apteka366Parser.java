package com.appleguard.pharmacy_parser.parsers;

import com.appleguard.pharmacy_parser.additionalTools.ParsersTools;
import com.appleguard.pharmacy_parser.additionalTools.Translator;
import com.appleguard.pharmacy_parser.entity.Drug;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class Apteka366Parser implements Parser {
    @Autowired
    ParsersTools tools;
    @Autowired
    Translator translator;
    @Override
    public List<Drug> parse(String inputDrug, String city) {

        List<Drug> drugsList = new ArrayList<>();

        WebClient webClient = tools.getWebClient();
        inputDrug = translator.translate(inputDrug);
        try {
            if(city.equals("Москва")) {
                HtmlPage page = webClient.getPage("https://366.ru/");
            } else if (city.equals("Санкт-Петербург")){
                HtmlPage page = webClient.getPage("https://366.ru/spb/");
            } else return drugsList;
            HtmlPage page = webClient.getPage("https://366.ru/search/?text="+inputDrug);
            HtmlDivision div = (HtmlDivision) page.getFirstByXPath
                    ("/html/body/main/div[9]/div/div[2]/div[2]/div/div[2]/div[3]/div");
            if (div!=null) {
                for (int i = 1; i <= div.getChildElementCount(); i++) {
                    try {
                    DomElement item = div.getFirstByXPath
                            ("/html/body/main/div[9]/div/div[2]/div[2]/div/div[2]/div[3]/div/div[" + i + "]");
                    DomElement itemName = div
                            .getFirstByXPath("/html/body/main/div[9]/div/div[2]/div[2]/div/div[2]/div[3]/div/div[" +
                                    i + "]/div[2]/div[1]/a");
                    DomElement itemPrice = item.getFirstElementChild()
                            .getNextElementSibling().getNextElementSibling()
                            .getFirstElementChild().getNextElementSibling().getFirstElementChild();
                        DomElement itemOriginalName = div
                            .getFirstByXPath("/html/body/main/div[9]/div/div[2]/div[2]/div/div[2]/div[3]/div/div[" +
                                    i + "]/div[2]/div[1]/div/div[1]");
                    if (itemName != null && itemPrice != null) {
                        String name = tools.editedName(itemName);
                        double price = tools.editedPrice(itemPrice);
                        String originalName;
                        if (itemOriginalName!=null) {
                            originalName = tools.editedOriginalName(itemOriginalName);
                        }else originalName = null;
                        Drug drug = tools.newDrug(name, price, "Аптека 36,6",originalName);
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
