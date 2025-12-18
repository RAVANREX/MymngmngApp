package com.example.mnymng.DB.models;

import com.example.mnymng.DB.enums.CategoryType; // Assuming this enum is used for type
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RecurringTestData {

    public static List<Recurring> getRecurrings() {
        List<Recurring> recurrings = new ArrayList<>();
        long currentTime = new Date().getTime();

        // Sample Frequencies (assuming these are string constants in your Recurring model or a helper class)
        String FREQ_MONTHLY = "MONTHLY";
        String FREQ_WEEKLY = "WEEKLY";
        String FREQ_YEARLY = "YEARLY";
        String FREQ_DAILY = "DAILY";
        String FREQ_BI_WEEKLY = "BI_WEEKLY";

        // Assuming constructor: Recurring(Long id, String description, double amount, CategoryType type, Long categoryId, Long accountId, String frequency, Date startDate, Date endDate, Date nextDueDate, String notes, boolean isActive)

        // Monthly Expenses
        recurrings.add(new Recurring(1L, "Netflix Premium", 19.99, CategoryType.EXPENSE, 15L, 4L, FREQ_MONTHLY, new Date(currentTime - TimeUnit.DAYS.toMillis(90)), null, new Date(currentTime + TimeUnit.DAYS.toMillis(10)), "Family streaming plan", true));
        recurrings.add(new Recurring(2L, "Gym Membership - GoodLife", 55.00, CategoryType.EXPENSE, 11L, 1L, FREQ_MONTHLY, new Date(currentTime - TimeUnit.DAYS.toMillis(180)), null, new Date(currentTime + TimeUnit.DAYS.toMillis(5)), "All-access fitness", true));
        recurrings.add(new Recurring(3L, "Phone Bill - Rogers", 85.00, CategoryType.EXPENSE, 8L, 4L, FREQ_MONTHLY, new Date(currentTime - TimeUnit.DAYS.toMillis(300)), null, new Date(currentTime + TimeUnit.DAYS.toMillis(12)), "Mobile + Data plan", true));
        recurrings.add(new Recurring(4L, "Internet Bill - Bell", 99.00, CategoryType.EXPENSE, 8L, 1L, FREQ_MONTHLY, new Date(currentTime - TimeUnit.DAYS.toMillis(200)), null, new Date(currentTime + TimeUnit.DAYS.toMillis(18)), "Fibre internet", true));
        recurrings.add(new Recurring(5L, "Rent Payment", 2200.00, CategoryType.EXPENSE, 7L, 1L, FREQ_MONTHLY, new Date(currentTime - TimeUnit.DAYS.toMillis(30)), null, new Date(currentTime + TimeUnit.DAYS.toMillis(1)), "Apartment rent", true));
        
        // Monthly Income
        recurrings.add(new Recurring(6L, "Salary Deposit - Company Inc.", 4500.00, CategoryType.INCOME, 1L, 1L, FREQ_MONTHLY, new Date(currentTime - TimeUnit.DAYS.toMillis(150)), null, new Date(currentTime + TimeUnit.DAYS.toMillis(15)), "Regular monthly paycheck", true));
        recurrings.add(new Recurring(7L, "Rental Income - Unit B", 1200.00, CategoryType.INCOME, 4L, 2L, FREQ_MONTHLY, new Date(currentTime - TimeUnit.DAYS.toMillis(60)), null, new Date(currentTime + TimeUnit.DAYS.toMillis(3)), "Rent from basement tenant", true));

        // Weekly Expenses
        recurrings.add(new Recurring(8L, "Groceries - Superstore Run", 150.00, CategoryType.EXPENSE, 6L, 3L, FREQ_WEEKLY, new Date(currentTime - TimeUnit.DAYS.toMillis(40)), null, new Date(currentTime + TimeUnit.DAYS.toMillis(2)), "Weekly food and supplies", true));
        recurrings.add(new Recurring(9L, "House Cleaning Service", 80.00, CategoryType.EXPENSE, 8L, 1L, FREQ_BI_WEEKLY, new Date(currentTime - TimeUnit.DAYS.toMillis(20)), null, new Date(currentTime + TimeUnit.DAYS.toMillis(6)), "Bi-weekly house cleaning", false)); // Inactive

        // Yearly Expenses
        recurrings.add(new Recurring(10L, "Car Insurance - TD Auto", 1200.00, CategoryType.EXPENSE, 9L, 4L, FREQ_YEARLY, new Date(currentTime - TimeUnit.DAYS.toMillis(300)), null, new Date(currentTime + TimeUnit.DAYS.toMillis(60)), "Annual vehicle insurance premium", true));
        recurrings.add(new Recurring(11L, "Amazon Prime Membership", 99.00, CategoryType.EXPENSE, 15L, 4L, FREQ_YEARLY, new Date(currentTime - TimeUnit.DAYS.toMillis(100)), null, new Date(currentTime + TimeUnit.DAYS.toMillis(200)), "Annual subscription for Prime benefits", true));
        
        // Daily (less common, but for variety)
        recurrings.add(new Recurring(12L, "Daily Newspaper Subscription", 1.50, CategoryType.EXPENSE, 15L, 6L, FREQ_DAILY, new Date(currentTime - TimeUnit.DAYS.toMillis(10)), null, new Date(currentTime + TimeUnit.MILLISECONDS.toMillis(12 * 60 * 60 * 1000)), "Local newspaper delivery", true));

        // More Monthly Expenses
        recurrings.add(new Recurring(13L, "Spotify Premium", 10.99, CategoryType.EXPENSE, 15L, 5L, FREQ_MONTHLY, new Date(currentTime - TimeUnit.DAYS.toMillis(50)), null, new Date(currentTime + TimeUnit.DAYS.toMillis(22)), "Music streaming service", true));
        recurrings.add(new Recurring(14L, "iCloud Storage 200GB", 3.99, CategoryType.EXPENSE, 8L, 5L, FREQ_MONTHLY, new Date(currentTime - TimeUnit.DAYS.toMillis(25)), null, new Date(currentTime + TimeUnit.DAYS.toMillis(8)), "Cloud storage for backups", true));
        recurrings.add(new Recurring(15L, "Patreon - Artist Support", 10.00, CategoryType.EXPENSE, 11L, 6L, FREQ_MONTHLY, new Date(currentTime - TimeUnit.DAYS.toMillis(15)), null, new Date(currentTime + TimeUnit.DAYS.toMillis(13)), "Support for online creator", true));
        recurrings.add(new Recurring(16L, "Adobe Creative Cloud", 25.00, CategoryType.EXPENSE, 15L, 4L, FREQ_MONTHLY, new Date(currentTime - TimeUnit.DAYS.toMillis(70)), null, new Date(currentTime + TimeUnit.DAYS.toMillis(17)), "Photoshop & Lightroom Student Plan", true)); // Assuming discounted
        
        // More Monthly Income
        recurrings.add(new Recurring(17L, "Freelance Consulting Retainer", 750.00, CategoryType.INCOME, 2L, 14L, FREQ_MONTHLY, new Date(currentTime - TimeUnit.DAYS.toMillis(45)), null, new Date(currentTime + TimeUnit.DAYS.toMillis(20)), "Monthly consulting fee for Client X", true));
        
        // More Yearly
        recurrings.add(new Recurring(18L, "Website Domain Renewal - GoDaddy", 22.00, CategoryType.EXPENSE, 8L, 17L, FREQ_YEARLY, new Date(currentTime - TimeUnit.DAYS.toMillis(250)), null, new Date(currentTime + TimeUnit.DAYS.toMillis(110)), "Annual domain name cost", true));
        recurrings.add(new Recurring(19L, "Professional Association Membership", 250.00, CategoryType.EXPENSE, 13L, 14L, FREQ_YEARLY, new Date(currentTime - TimeUnit.DAYS.toMillis(120)), null, new Date(currentTime + TimeUnit.DAYS.toMillis(240)), "Annual fee for Engineering Assoc.", true));

        // Another Weekly
        recurrings.add(new Recurring(20L, "Kid's Allowance", 15.00, CategoryType.EXPENSE, 11L, 6L, FREQ_WEEKLY, new Date(currentTime - TimeUnit.DAYS.toMillis(30)), null, new Date(currentTime + TimeUnit.DAYS.toMillis(4)), "Weekly pocket money for child", true));
        
        // Loan Payment (Monthly Expense)
        recurrings.add(new Recurring(21L, "Car Loan Payment - Honda Finance", 450.00, CategoryType.EXPENSE, 9L, 9L, FREQ_MONTHLY, new Date(currentTime - TimeUnit.DAYS.toMillis(600)), new Date(currentTime + TimeUnit.DAYS.toMillis(365 * 2)), new Date(currentTime + TimeUnit.DAYS.toMillis(25)), "Monthly car payment installment", true));
        recurrings.add(new Recurring(22L, "Student Loan Repayment", 250.00, CategoryType.EXPENSE, 13L, 10L, FREQ_MONTHLY, new Date(currentTime - TimeUnit.DAYS.toMillis(100)), new Date(currentTime + TimeUnit.DAYS.toMillis(365 * 7)), new Date(currentTime + TimeUnit.DAYS.toMillis(14)), "Monthly student loan installment", true));

        return recurrings;
    }
}
