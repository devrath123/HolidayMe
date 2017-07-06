package com.holidayme.data;

public class CurrencyObject implements Item {

    private String name;

    private String languageCode;

    private String arabiclanguage;

    boolean isCheck = false;

    public CurrencyObject(String name, String languageCode, String arabiclanguage) {
        this.name = name;
        this.languageCode = languageCode;
        this.arabiclanguage = arabiclanguage;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setIsCheck(boolean isCheck) {
        this.isCheck = isCheck;
    }

    @Override
    public boolean isSection() {
        return false;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }


    public String getArabiclanguage() {
        return arabiclanguage;
    }

    public void setArabiclanguage(String arabiclanguage) {
        this.arabiclanguage = arabiclanguage;
    }
}
