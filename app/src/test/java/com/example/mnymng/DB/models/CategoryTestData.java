package com.example.mnymng.DB.models;

import com.example.mnymng.DB.enums.CategoryType; // Assuming this enum exists
import java.util.ArrayList;
import java.util.List;

public class CategoryTestData {

    public static List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();

        // Assuming constructor: Category(Long id, String name, CategoryType type, String icon, String notes)
        // Income Categories (5)
        categories.add(new Category(1L, "Salary", CategoryType.INCOME, "🏠", "Monthly regular salary"));
        categories.add(new Category(2L, "Freelance Project", CategoryType.INCOME, "🏠", "Income from side projects"));
        categories.add(new Category(3L, "Bonus", CategoryType.INCOME, "🏠", "Annual or performance bonus"));
        categories.add(new Category(4L, "Rental Income", CategoryType.INCOME, "🏠", "Income from property rent"));
        categories.add(new Category(5L, "Dividends", CategoryType.INCOME, "🏠", "Income from stock dividends"));

        // Expense Categories (10)
        categories.add(new Category(6L, "Groceries", CategoryType.EXPENSE, "🖼️", "Weekly grocery shopping"));
        categories.add(new Category(7L, "Rent/Mortgage", CategoryType.EXPENSE, "🖼️", "Monthly housing payment"));
        categories.add(new Category(8L, "Utilities", CategoryType.EXPENSE, "🖼️", "Electricity, water, gas, internet bills"));
        categories.add(new Category(9L, "Transportation", CategoryType.EXPENSE, "🖼️", "Public transport, fuel, car maintenance"));
        categories.add(new Category(10L, "Dining Out", CategoryType.EXPENSE, "🖼️", "Restaurants, cafes, takeaways"));
        categories.add(new Category(11L, "Entertainment", CategoryType.EXPENSE, "🖼️", "Movies, concerts, events, hobbies"));
        categories.add(new Category(12L, "Healthcare", CategoryType.EXPENSE, "🖼️", "Medical expenses, insurance, pharmacy"));
        categories.add(new Category(13L, "Education", CategoryType.EXPENSE, "🖼️", "Tuition fees, books, courses"));
        categories.add(new Category(14L, "Shopping", CategoryType.EXPENSE, "🖼️", "Clothing, electronics, household items"));
        categories.add(new Category(15L, "Subscriptions", CategoryType.EXPENSE, "🖼️", "Streaming services, software, memberships"));
        
        // Investment Categories (5)
        categories.add(new Category(16L, "Stocks", CategoryType.INVESTMENT, "ic_stocks", "Investment in stock market"));
        categories.add(new Category(17L, "Mutual Funds", CategoryType.INVESTMENT, "ic_mutual_funds", "Investment in mutual funds"));
        categories.add(new Category(18L, "Real Estate Investment", CategoryType.INVESTMENT, "ic_real_estate", "Investment in property (not primary residence)"));
        categories.add(new Category(19L, "Cryptocurrency", CategoryType.INVESTMENT, "ic_crypto", "Investment in cryptocurrencies"));
        categories.add(new Category(20L, "Bonds", CategoryType.INVESTMENT, "ic_bonds", "Investment in government or corporate bonds"));
        
        // Other/Miscellaneous
        categories.add(new Category(21L, "Gifts Received", CategoryType.INCOME, "ic_gift_received", "Monetary gifts received"));
        categories.add(new Category(22L, "Gifts Given", CategoryType.EXPENSE, "ic_gift_given", "Presents for others"));
        categories.add(new Category(23L, "Travel", CategoryType.EXPENSE, "ic_travel", "Vacation and business travel expenses"));
        categories.add(new Category(24L, "Charity", CategoryType.EXPENSE, "ic_charity", "Donations to charitable organizations"));
        categories.add(new Category(25L, "Taxes", CategoryType.EXPENSE, "ic_taxes", "Income tax, property tax payments"));

        return categories;
    }
}
