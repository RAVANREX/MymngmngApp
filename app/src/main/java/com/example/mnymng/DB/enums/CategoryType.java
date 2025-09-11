package com.example.mnymng.DB.enums;

import java.io.Serializable; // Added import

public enum CategoryType implements Serializable { // Added implements Serializable
    INCOME,
    EXPENSE,
    TRIP,
    INSURANCE,
    INVESTMENT,
    LOAN,
    LENDING,
    ASSET

    // Add other category types as needed
}
