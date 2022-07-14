package com.appleguard.pharmacy_parser.additionalTools;

import com.appleguard.pharmacy_parser.entity.Drug;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.util.Cookie;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ParsersTools {
    public WebClient getWebClient() {
        try (final WebClient webClient = new WebClient(BrowserVersion.FIREFOX)) {
            webClient.getOptions().setJavaScriptEnabled(false);
            webClient.getOptions().setCssEnabled(false);
            return webClient;
        }
    }

    public void setCityByCookie(WebClient webClient, String cookieName,Cookie myCookie){
        CookieManager cookieManager = webClient.getCookieManager();
        Cookie cookie = cookieManager.getCookie(cookieName);
        cookieManager.removeCookie(cookie);
        cookieManager.addCookie(myCookie);
    }
    public String editedName( DomElement domName){
        String name = domName.getTextContent();
        name = name.trim();
        return name;
    }
    public String editedOriginalName( DomElement domOriginalName){
        String originalName = domOriginalName.getTextContent();
        originalName = originalName.trim();
        originalName = originalName.toLowerCase(Locale.ROOT);
        return originalName;
    }
    public double editedPrice(DomElement domPrice){
        String price = domPrice.getTextContent();
        StringBuilder stringBuilder = new StringBuilder();
        char[] priceCharArray = price.toCharArray();
        for(char ch:priceCharArray){
            if(Character.isDigit(ch) || ch=='.'){
                stringBuilder.append(ch);
                if(stringBuilder.toString().contains(".") && ch != '.' && Character.isDigit(ch)) break;
            }
        }
        String editedPrice = stringBuilder.toString();
        return Double.parseDouble(editedPrice);
    }
    public Drug newDrug(String name, double priceInDouble, String drugStore, String originalName){
        Drug drug = new Drug();
        drug.setPrice(priceInDouble);
        drug.setFullName(name);
        drug.setDrugStore(drugStore);
        drug.setOriginalName(originalName);
        return drug;
    }
}
