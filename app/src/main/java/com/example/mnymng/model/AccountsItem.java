package com.example.mnymng.model;

public class AccountsItem {
    private String name;
    private Double howMuch;
    private Double remaining;

    public AccountsItem( String name, Double howMuch, Double remaining) {
        this.name = name;
        this.howMuch = howMuch;
        this.remaining = remaining;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getHowMuch() {
        return howMuch;
    }

    public void setHowMuch(Double howMuch) {
        this.howMuch = howMuch;
    }

    public Double getRemaining() {
        return remaining;
    }

    public void setRemaining(Double remaining) {
        this.remaining = remaining;
    }
}
