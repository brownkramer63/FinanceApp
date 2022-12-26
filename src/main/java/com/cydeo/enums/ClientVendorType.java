package com.cydeo.enums;

public enum ClientVendorType {

    Client("Client"), Vendor("Vendor");

    private final String value;


    ClientVendorType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
