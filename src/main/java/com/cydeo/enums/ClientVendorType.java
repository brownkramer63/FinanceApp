package com.cydeo.enums;

public enum ClientVendorType {

    CLIENT ("Client"), VENDOR ("Vendor");

    private final String value;


    ClientVendorType(String value) {
        this.value = value;
    }
}
