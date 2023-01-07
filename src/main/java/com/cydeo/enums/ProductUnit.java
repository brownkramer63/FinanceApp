package com.cydeo.enums;

import lombok.Getter;


public enum ProductUnit {
    LBS("Libre"),GALLON("Gallon"),PCS("PCs"),KG("Kilogram"),METER("Meter"),INCH("Inch"),FEET("Feet");

    private final String value;

    public String getValue() {
        return value;
    }

    ProductUnit (String value) {
        this.value = value;
    }




}
