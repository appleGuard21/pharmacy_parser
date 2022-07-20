package com.appleguard.pharmacy_parser.parsers;

import com.appleguard.pharmacy_parser.additionalTools.City;
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
public class SerdceParser implements Parser{
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
            HtmlPage page;
            switch (city) {
                case MOSCOW, ST_PETERSBURG -> page = webClient.getPage("https://sr.farm/search/?q="+inputDrug);
                default -> {
                    return drugsList;
                }
            }
            HtmlDivision div = (HtmlDivision) page.getFirstByXPath("/html/body/main/div/div[5]/div/div");
            if (div!=null) {
                for (int i = 1; i <= div.getChildElementCount(); i++) {
                    try {
                    DomElement item = div.getFirstByXPath("/html/body/main/div/div[5]/div/div/div[" + i + "]");
                    DomElement itemName = item.getFirstElementChild().getFirstElementChild()
                            .getFirstElementChild().getFirstElementChild().getFirstElementChild()
                            .getFirstElementChild().getFirstElementChild();
                    DomElement itemPrice = item.getFirstElementChild().getFirstElementChild()
                            .getFirstElementChild().getLastElementChild().getFirstElementChild()
                            .getFirstElementChild().getFirstElementChild();
                    DomElement itemOriginalName = item.getFirstElementChild().getFirstElementChild()
                            .getFirstElementChild().getFirstElementChild().getNextElementSibling()
                            .getFirstElementChild().getLastElementChild().getFirstElementChild();
                    if (itemName != null && itemPrice != null) {
                        String name = tools.editedName(itemName);
                        double price = tools.editedPrice(itemPrice);
                        String originalName = tools.editedOriginalName(itemOriginalName);
                        Drug drug = tools.newDrug(name, price, "Сердце россии",originalName);
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

