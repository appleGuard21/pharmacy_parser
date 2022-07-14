package com.appleguard.pharmacy_parser.additionalTools;

public enum City {
    MOSCOW_STOLICHKI("1"),
    PITER_STOLICHKI("77"),
    STOLETOV_PITER("20238"),
    STOLETOV_MOSCOW("20237");
    final String city;

    City(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}
