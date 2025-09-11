package com.example.mnymng.DB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.mnymng.DB.dao.AccountDao;
import com.example.mnymng.DB.dao.CategoryDao;
import com.example.mnymng.DB.dao.FuturePlanDao;
import com.example.mnymng.DB.dao.RecurringDao;
import com.example.mnymng.DB.dao.TransactionDao;
import com.example.mnymng.DB.dao.TransactionSummaryDao;
// import com.example.mnymng.DB.enums.CategoryType; // Removed unused import
import com.example.mnymng.DB.enums.CategoryType;
import com.example.mnymng.DB.models.Account;
import com.example.mnymng.DB.models.Category;
import com.example.mnymng.DB.models.FuturePlan;
import com.example.mnymng.DB.models.Recurring;
import com.example.mnymng.DB.models.Transaction;
import com.example.mnymng.DB.models.TransactionSummary;
import com.example.mnymng.DB.utils.DateConverter;
import com.example.mnymng.DB.utils.EnumConverters;

import java.util.concurrent.Executors;

@Database(entities = {Account.class, Category.class, Transaction.class, TransactionSummary.class, Recurring.class, FuturePlan.class},
        version = 1, exportSchema = false)
@TypeConverters({DateConverter.class, EnumConverters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract AccountDao accountDao();
    public abstract CategoryDao categoryDao();
    public abstract TransactionDao transactionDao();
    public abstract TransactionSummaryDao transactionSummaryDao();
    public abstract RecurringDao recurringDao();
    public abstract FuturePlanDao futurePlanDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "money_management_db")
                            .addCallback(sRoomDatabaseCallback) // Callback for pre-populating data
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() { // Made final
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // If you want to keep data through app restarts, use Executors.newSingleThreadScheduledExecutor().execute or similar
            Executors.newSingleThreadExecutor().execute(() -> {
                // Populate the database in the background.
                // Example: Add default categories
                CategoryDao dao = INSTANCE.categoryDao();

                // Income Categories (5)
               dao.insert(new Category( "Salary", CategoryType.INCOME, "🏠", "Monthly regular salary"));
               dao.insert(new Category( "Freelance Project", CategoryType.INCOME, "🏠", "Income from side projects"));
               dao.insert(new Category( "Bonus", CategoryType.INCOME, "🏠", "Annual or performance bonus"));
               dao.insert(new Category( "Rental Income", CategoryType.INCOME, "🏠", "Income from property rent"));
               dao.insert(new Category( "Dividends", CategoryType.INCOME, "🏠", "Income from stock dividends"));

                // Expense Categories (10)
               dao.insert(new Category( "Groceries", CategoryType.EXPENSE, "🖼️", "Weekly grocery shopping"));
               dao.insert(new Category( "Rent/Mortgage", CategoryType.EXPENSE, "🖼️", "Monthly housing payment"));
               dao.insert(new Category( "Utilities", CategoryType.EXPENSE, "🖼️", "Electricity, water, gas, internet bills"));
               dao.insert(new Category( "Transportation", CategoryType.EXPENSE, "🖼️", "Public transport, fuel, car maintenance"));
               dao.insert(new Category( "Dining Out", CategoryType.EXPENSE, "🖼️", "Restaurants, cafes, takeaways"));
               dao.insert(new Category( "Entertainment", CategoryType.EXPENSE, "🖼️", "Movies, concerts, events, hobbies"));
               dao.insert(new Category( "Healthcare", CategoryType.EXPENSE, "🖼️", "Medical expenses, insurance, pharmacy"));
               dao.insert(new Category( "Education", CategoryType.EXPENSE, "🖼️", "Tuition fees, books, courses"));
               dao.insert(new Category( "Shopping", CategoryType.EXPENSE, "🖼️", "Clothing, electronics, household items"));
               dao.insert(new Category( "Subscriptions", CategoryType.EXPENSE, "🖼️", "Streaming services, software, memberships"));

                // Investment Categories (5)
               dao.insert(new Category( "Stocks", CategoryType.INVESTMENT, "🖼️", "Investment in stock market"));
               dao.insert(new Category( "Mutual Funds", CategoryType.INVESTMENT, "🖼️", "Investment in mutual funds"));
               dao.insert(new Category( "Real Estate Investment", CategoryType.INVESTMENT, "🖼️", "Investment in property (not primary residence)"));
               dao.insert(new Category( "Cryptocurrency", CategoryType.INVESTMENT, "🖼️", "Investment in cryptocurrencies"));
               dao.insert(new Category( "Bonds", CategoryType.INVESTMENT, "🖼️", "Investment in government or corporate bonds"));

                // Add more default data for other tables if needed
            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // You could also do checks or other operations when the DB is opened
        }
    };
}
