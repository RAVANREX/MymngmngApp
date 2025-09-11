package com.example.mnymng.DB.models;

import com.example.mnymng.DB.enums.AccountType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AccountTestData {

    public static List<Account> getAccounts() {
        List<Account> accounts = new ArrayList<>();
        long currentTime = new Date().getTime();

        // Constructor: Account(long account_id, String account_name, AccountType account_type, double account_opening_balance, double account_current_balance, double account_interest_rate, Date account_due_date, Date account_next_due_date, String account_currency, Date account_updated_date)

        accounts.add(new Account(1L, "Primary Checking", AccountType.BANK, 1500.00, 1850.75, 0.005, null, null, "USD", new Date(currentTime - TimeUnit.DAYS.toMillis(1))));
        accounts.add(new Account(2L, "High-Yield Savings", AccountType.BANK, 10000.00, 10250.00, 0.045, null, null, "USD", new Date(currentTime - TimeUnit.DAYS.toMillis(2))));
        accounts.add(new Account(3L, "Emergency Fund", AccountType.BANK, 20000.00, 20100.00, 0.040, null, null, "USD", new Date(currentTime - TimeUnit.DAYS.toMillis(5))));
        accounts.add(new Account(4L, "Wallet Cash", AccountType.WALLET, 200.00, 120.50, 0.0, null, null, "USD", new Date(currentTime - TimeUnit.DAYS.toMillis(0))));
        accounts.add(new Account(5L, "Visa Rewards Card", AccountType.CREDIT_CARD, 0.00, -350.45, 0.199, new Date(currentTime + TimeUnit.DAYS.toMillis(20)), new Date(currentTime + TimeUnit.DAYS.toMillis(50)), "USD", new Date(currentTime - TimeUnit.DAYS.toMillis(3))));
        accounts.add(new Account(6L, "Mastercard Travel", AccountType.CREDIT_CARD, 0.00, -125.00, 0.225, new Date(currentTime + TimeUnit.DAYS.toMillis(15)), new Date(currentTime + TimeUnit.DAYS.toMillis(45)), "USD", new Date(currentTime - TimeUnit.DAYS.toMillis(1))));
        accounts.add(new Account(7L, "Car Loan", AccountType.LOAN, -15000.00, -14500.00, 0.035, new Date(currentTime + TimeUnit.DAYS.toMillis(25)), new Date(currentTime + TimeUnit.DAYS.toMillis(55)), "USD", new Date(currentTime - TimeUnit.DAYS.toMillis(10))));
        accounts.add(new Account(8L, "Student Loan", AccountType.LOAN, -25000.00, -24800.00, 0.042, new Date(currentTime + TimeUnit.DAYS.toMillis(10)), new Date(currentTime + TimeUnit.DAYS.toMillis(40)), "USD", new Date(currentTime - TimeUnit.DAYS.toMillis(12))));
        accounts.add(new Account(9L, "Investment Portfolio", AccountType.BANK, 5000.00, 5300.00, 0.0, null, null, "USD", new Date(currentTime - TimeUnit.DAYS.toMillis(7)))); // Using BANK as placeholder if no specific investment type
        accounts.add(new Account(10L, "PayPal Balance", AccountType.WALLET, 300.00, 250.90, 0.0, null, null, "USD", new Date(currentTime - TimeUnit.DAYS.toMillis(1))));
        accounts.add(new Account(11L, "Gift Card - Amazon", AccountType.WALLET, 50.00, 25.00, 0.0, null, null, "USD", new Date(currentTime - TimeUnit.DAYS.toMillis(30))));
        accounts.add(new Account(12L, "Business Checking", AccountType.BANK, 7500.00, 8200.00, 0.006, null, null, "USD", new Date(currentTime - TimeUnit.DAYS.toMillis(4))));
        accounts.add(new Account(13L, "Mortgage", AccountType.LOAN, -250000.00, -249000.00, 0.030, new Date(currentTime + TimeUnit.DAYS.toMillis(1)), new Date(currentTime + TimeUnit.DAYS.toMillis(31)), "USD", new Date(currentTime - TimeUnit.DAYS.toMillis(20))));
        accounts.add(new Account(14L, "Vacation Fund", AccountType.BANK, 1000.00, 1100.50, 0.020, null, null, "USD", new Date(currentTime - TimeUnit.DAYS.toMillis(6))));
        accounts.add(new Account(15L, "Store Credit Card", AccountType.CREDIT_CARD, 0.00, -75.00, 0.249, new Date(currentTime + TimeUnit.DAYS.toMillis(5)), new Date(currentTime + TimeUnit.DAYS.toMillis(35)), "USD", new Date(currentTime - TimeUnit.DAYS.toMillis(2))));
        accounts.add(new Account(16L, "Lending to Friend", AccountType.LENDING, 200.00, 200.00, 0.0, new Date(currentTime + TimeUnit.DAYS.toMillis(60)), null, "USD", new Date(currentTime - TimeUnit.DAYS.toMillis(10))));
        accounts.add(new Account(17L, "Kids Savings Account", AccountType.BANK, 500.00, 520.00, 0.015, null, null, "USD", new Date(currentTime - TimeUnit.DAYS.toMillis(9))));
        accounts.add(new Account(18L, "Crypto Wallet", AccountType.WALLET, 1200.00, 1150.70, 0.0, null, null, "BTC", new Date(currentTime - TimeUnit.DAYS.toMillis(1)))); // Different currency
        accounts.add(new Account(19L, "Business Credit Card", AccountType.CREDIT_CARD, 0.00, -1200.00, 0.180, new Date(currentTime + TimeUnit.DAYS.toMillis(22)), new Date(currentTime + TimeUnit.DAYS.toMillis(52)), "USD", new Date(currentTime - TimeUnit.DAYS.toMillis(5))));
        accounts.add(new Account(20L, "Loan from Family", AccountType.LENDING, -5000.00, -4800.00, 0.01, new Date(currentTime + TimeUnit.DAYS.toMillis(180)), null, "USD", new Date(currentTime - TimeUnit.DAYS.toMillis(45)))); // Negative balance for LENDING when you are the borrower
        accounts.add(new Account(21L, "Security Deposit", AccountType.BANK, 1000.00, 1000.00, 0.0, null, null, "USD", new Date(currentTime - TimeUnit.DAYS.toMillis(300)))); // Held by landlord, but your asset

        return accounts;
    }
}
