package com.holidayme.data;

import java.util.List;

/**
 * Created by devrath.rathee on 23-02-2017.
 */

public class GetawayBean {
    String title,currency;
    double oldPrice,newPrice;
    int quantity;
    List<String> inclusionList;

    public GetawayBean(String title, String currency, double oldPrice, double newPrice, int quantity, List<String> inclusionList) {
        this.title = title;
        this.currency = currency;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.quantity = quantity;
        this.inclusionList = inclusionList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public List<String> getInclusionList() {
        return inclusionList;
    }

    public void setInclusionList(List<String> inclusionList) {
        this.inclusionList = inclusionList;
    }
}
