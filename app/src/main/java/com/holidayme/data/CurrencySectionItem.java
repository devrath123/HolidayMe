package com.holidayme.data;

/**
 * Created by santosh.patar on 18-08-2015.
 */
public class CurrencySectionItem implements Item {

    @Override
    public boolean isSection() {
        return true;
    }

    private final String title;

    public CurrencySectionItem(String title) {
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

}
