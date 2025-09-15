package com.example.mnymng.model;

import com.example.mnymng.DB.enums.Currency;

public class ListItem {
    private String itemName;
    private Double itemValue;
    private String currency;
    private String itemType;
    private String Date;

    public ListItem(String itemName, Double itemValue, String currency, String itemType, String date) {
        this.itemName = itemName;
        this.itemValue = itemValue;
        this.currency = currency;
        this.itemType = itemType;
        Date = date;
    }

    public String getItemName() {
        return itemName;
    }

    public Double getItemValue() {
        return itemValue;
    }

    public String getcurrency() {
        return currency;
    }

    public String getItemType() {
        return itemType;
    }

    public String getDate() {
        return Date;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemValue(Double itemValue) {
        this.itemValue = itemValue;
    }

    public void setcurrency(String currency) {
        this.currency = currency;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public void setDate(String date) {
        Date = date;
    }




}
