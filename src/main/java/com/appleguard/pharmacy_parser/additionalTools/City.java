package com.appleguard.pharmacy_parser.additionalTools;

public enum City {
    MOSCOW("������"),
    ST_PETERSBURG("�����-���������");
    private final String city;

    City(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}
