package com.appleguard.pharmacy_parser.additionalTools;

import org.springframework.stereotype.Component;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

@Component
public class Translator {
    public String translate(String str){
        byte[] getBytesFromString = str.getBytes(StandardCharsets.UTF_8);
        BigInteger bigInteger = new BigInteger(1, getBytesFromString);
        String convertedResult = String.format("%X", bigInteger);
        char[] myArray = convertedResult.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i =0;i< myArray.length-1;i=i+2){
            stringBuilder.append("%");
            stringBuilder.append(myArray[i]);
            stringBuilder.append(myArray[i+1]);
        }
        return stringBuilder.toString();
    }
}
