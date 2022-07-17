package com.appleguard.pharmacy_parser.additionalTools;

public enum City {
    MOSCOW_STOLICHKI("1"),
    PITER_STOLICHKI("77"),
    PITER_STOLETOV("20238"),
    MOSCOW_STOLETOV("20237");
    private final String city;

    City(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}
