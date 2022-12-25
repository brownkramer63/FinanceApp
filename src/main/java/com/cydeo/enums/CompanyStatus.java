package com.cydeo.enums;

public enum CompanyStatus {

    ACTIVE("Actıve"), PASSIVE("Passive");

    private final String value;

    CompanyStatus(String value){
        this.value=value;
    }

    public String getValue(){
        return value;
    }


}
