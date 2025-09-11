package com.example.mnymng.DB.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TransactionSummaryTestData {

    // Assuming a PeriodType enum or constants might exist, e.g.,
    // public enum PeriodType { DAILY, WEEKLY, MONTHLY, YEARLY, CUSTOM }
    // For simplicity, using Strings here. These should match your actual implementation.
    public static final String PERIOD_DAILY = "DAILY";
    public static final String PERIOD_WEEKLY = "WEEKLY";
    public static final String PERIOD_MONTHLY = "MONTHLY";
    public static final String PERIOD_YEARLY = "YEARLY";

    // Assuming constructor: TransactionSummary(Long summaryId, Date startDate, Date endDate, double totalIncome, double totalExpense, double netChange, String periodType, Long accountId, Long categoryId, Date createdAt)
    // For DAILY, endDate might be null or same as startDate.

    public static List<TransactionSummary> getTransactionSummaries() {
        List<TransactionSummary> summaries = new ArrayList<>();
        long currentTime = new Date().getTime();


        return summaries;
    }
}
