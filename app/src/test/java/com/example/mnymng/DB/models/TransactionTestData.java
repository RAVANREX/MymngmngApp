package com.example.mnymng.DB.models;

import com.example.mnymng.DB.enums.CategoryType; // Assuming this enum exists
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TransactionTestData {

    public static List<Transaction> getTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        long currentTime = new Date().getTime();

        return transactions;
    }
}
