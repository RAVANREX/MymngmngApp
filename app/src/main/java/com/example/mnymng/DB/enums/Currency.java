package com.example.mnymng.DB.enums;

public enum Currency {
    USD("United States Dollar", "$"),
    EUR("Euro", "€"),
    JPY("Japanese Yen", "¥"),
    GBP("British Pound Sterling", "£"),
    AUD("Australian Dollar", "A$"),
    CAD("Canadian Dollar", "C$"),
    CHF("Swiss Franc", "CHF"),
    CNY("Chinese Yuan", "¥"),
    INR("Indian Rupee", "₹"),
    BRL("Brazilian Real", "R$"),
    RUB("Russian Ruble", "₽"),
    KRW("South Korean Won", "₩"),
    SGD("Singapore Dollar", "S$"),
    // Add other currencies as needed
    OTHER("Other", "");

    private final String currencyName;
    private final String symbol;

    Currency(String currencyName, String symbol) {
        this.currencyName = currencyName;
        this.symbol = symbol;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return currencyName + " (" + symbol + ")";
    }
}

