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
import com.example.mnymng.DB.dao.NotificationDao;
import com.example.mnymng.DB.dao.RecurringDao;
import com.example.mnymng.DB.dao.TransactionDao;
import com.example.mnymng.DB.dao.TransactionSummaryDao;
import com.example.mnymng.DB.enums.AccountType;
import com.example.mnymng.DB.enums.CategoryType;
import com.example.mnymng.DB.enums.RecurringFrequency;
import com.example.mnymng.DB.enums.TransactionType;
import com.example.mnymng.DB.models.Account;
import com.example.mnymng.DB.models.Category;
import com.example.mnymng.DB.models.FuturePlan;
import com.example.mnymng.DB.models.Notification;
import com.example.mnymng.DB.models.Recurring;
import com.example.mnymng.DB.models.Transaction;
import com.example.mnymng.DB.models.TransactionSummary;
import com.example.mnymng.DB.utils.DateConverter;
import com.example.mnymng.DB.utils.EnumConverters;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

@Database(entities = {Account.class, Category.class, Transaction.class, TransactionSummary.class, Recurring.class, FuturePlan.class, Notification.class},
        version = 2, exportSchema = false)
@TypeConverters({DateConverter.class, EnumConverters.class})
public abstract class AppDatabase extends RoomDatabase {

    public static ExecutorService databaseWriteExecutor;

    public abstract AccountDao accountDao();
    public abstract CategoryDao categoryDao();
    public abstract TransactionDao transactionDao();
    public abstract TransactionSummaryDao transactionSummaryDao();
    public abstract RecurringDao recurringDao();
    public abstract FuturePlanDao futurePlanDao();
    public abstract NotificationDao notificationDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "money_management_db")
                            .addCallback(sRoomDatabaseCallback)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public <T, R> R performDaoOperation(T dao, Function<T, R> operation) {
        return operation.apply(dao);
    }

    public <T> void performDaoAction(T dao, Consumer<T> action) {
        action.accept(dao);
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Executors.newSingleThreadExecutor().execute(() -> {
                CategoryDao categoryDao = INSTANCE.categoryDao();
                AccountDao accountDao = INSTANCE.accountDao();
                TransactionDao transactionDao = INSTANCE.transactionDao();
                RecurringDao recurringDao = INSTANCE.recurringDao();
                FuturePlanDao futurePlanDao = INSTANCE.futurePlanDao();
                NotificationDao notificationDao = INSTANCE.notificationDao();

//                // Add sample notifications
                notificationDao.insert(new Notification("Welcome to application", "Your financial journey starts now.", new Date(), false));
                notificationDao.insert(new Notification("Pro Tip", "Create a budget to stay on top of your spending.", new Date(System.currentTimeMillis() - TimeUnit.HOURS.toMillis(1)), true));

                // Original Income Categories (IDs: 1-5 assumed)
               categoryDao.insert(new Category( "Salary", CategoryType.INCOME, "🏠", "Monthly regular salary",null,null,null,null,null));
               categoryDao.insert(new Category( "Freelance Project", CategoryType.INCOME, "💻", "Income from side projects",null,null,null,null,null));
               categoryDao.insert(new Category( "Bonus", CategoryType.INCOME, "🎉", "Annual or performance bonus",null,null,null,null,null));
               categoryDao.insert(new Category( "Rental Income", CategoryType.INCOME, "🏘️", "Income from property rent",null,null,null,null,null));
               categoryDao.insert(new Category( "Dividends", CategoryType.INCOME, "📈", "Income from stock dividends",null,null,null,null,null));

                // Original Expense Categories (IDs: 6-15 assumed)
               categoryDao.insert(new Category( "Groceries", CategoryType.EXPENSE, "🛒", "Weekly grocery shopping",null,null,null,null,null));
               categoryDao.insert(new Category( "Rent/Mortgage", CategoryType.EXPENSE, "🔑", "Monthly housing payment",null,null,null,null,null));
               categoryDao.insert(new Category( "Utilities", CategoryType.EXPENSE, "💡", "Electricity, water, gas, internet bills",null,null,null,null,null));
               categoryDao.insert(new Category( "Transportation", CategoryType.EXPENSE, "🚗", "Public transport, fuel, car maintenance",null,null,null,null,null));
               categoryDao.insert(new Category( "Dining Out", CategoryType.EXPENSE, "🍔", "Restaurants, cafes, takeaways",null,null,null,null,null));
               categoryDao.insert(new Category( "Entertainment", CategoryType.EXPENSE, "🎬", "Movies, concerts, events, hobbies",null,null,null,null,null));
               categoryDao.insert(new Category( "Healthcare", CategoryType.EXPENSE, "⚕️", "Medical expenses, insurance, pharmacy",null,null,null,null,null));
               categoryDao.insert(new Category( "Education", CategoryType.EXPENSE, "📚", "Tuition fees, books, courses",null,null,null,null,null));
               categoryDao.insert(new Category( "Shopping", CategoryType.EXPENSE, "🛍️", "Clothing, electronics, household items",null,null,null,null,null));
               categoryDao.insert(new Category( "Subscriptions", CategoryType.EXPENSE, "💳", "Streaming services, software, memberships",null,null,null,null,null));

                // Original Investment Categories (IDs: 16-20 assumed)
               categoryDao.insert(new Category( "Stocks", CategoryType.TRIP, "📊", "Investment in stock market",null,null,null,null,null));
               categoryDao.insert(new Category( "Mutual Funds", CategoryType.TRIP, "📄", "Investment in mutual funds",null,null,null,null,null));
               categoryDao.insert(new Category( "Real Estate Investment", CategoryType.TRIP, "🏗️", "Investment in property (not primary residence)",null,null,null,null,null));
               categoryDao.insert(new Category( "Cryptocurrency", CategoryType.TRIP, "🔗", "Investment in cryptocurrencies",null,null,null,null,null));
               categoryDao.insert(new Category( "Bonds", CategoryType.TRIP, "📜", "Investment in government or corporate bonds",null,null,null,null,null));

                // NEW Income Categories (IDs starting from 21 assumed)
                categoryDao.insert(new Category("Consulting Gig", CategoryType.INCOME, "🧑‍🏫", "Income from consulting services", null, null, null, null, null));
                categoryDao.insert(new Category("Online Sales", CategoryType.INCOME, "🛒", "Revenue from e-commerce", null, null, null, null, null));
                categoryDao.insert(new Category("Gifts Received", CategoryType.INCOME, "🎁", "Monetary gifts", null, null, null, null, null));
                categoryDao.insert(new Category("Interest Earned", CategoryType.INCOME, "🏦", "Bank account interest", null, null, null, null, null));
                categoryDao.insert(new Category("Royalties", CategoryType.INCOME, "👑", "Income from creative work", null, null, null, null, null));
                categoryDao.insert(new Category("Cashback Rewards", CategoryType.INCOME, "💸", "Credit card cashback", null, null, null, null, null));
                categoryDao.insert(new Category("Government Support", CategoryType.INCOME, "🏛️", "Financial aid or grants", null, null, null, null, null));
                categoryDao.insert(new Category("Part-time Job", CategoryType.INCOME, "🧑‍💼", "Secondary employment income", null, null, null, null, null));
                categoryDao.insert(new Category("Stock Options", CategoryType.INCOME, "📈", "Exercised stock options", null, null, null, null, null));
                categoryDao.insert(new Category("Teaching/Tutoring", CategoryType.INCOME, "👩‍🏫", "Income from teaching", null, null, null, null, null));


                // NEW Expense Categories (IDs starting from 31 assumed)
                categoryDao.insert(new Category("Pet Care", CategoryType.EXPENSE, "🐕", "Food, vet, toys for pets", null, null, null, null, null));
                categoryDao.insert(new Category("Home Maintenance", CategoryType.EXPENSE, "🛠️", "Repairs and upkeep", null, null, null, null, null));
                categoryDao.insert(new Category("Travel & Vacation", CategoryType.EXPENSE, "✈️", "Holiday trips", null, null, null, null, null));
                categoryDao.insert(new Category("Gifts Given", CategoryType.EXPENSE, "🎁", "Presents for others", null, null, null, null, null));
                categoryDao.insert(new Category("Insurance", CategoryType.EXPENSE, "🛡️", "Home, auto, life insurance", null, null, null, null, null));
                categoryDao.insert(new Category("Childcare", CategoryType.EXPENSE, "👶", "Daycare, babysitting", null, null, null, null, null));
                categoryDao.insert(new Category("Fitness & Wellness", CategoryType.EXPENSE, "💪", "Gym, yoga, supplements", null, null, null, null, null));
                categoryDao.insert(new Category("Charity Donations", CategoryType.EXPENSE, "💖", "Contributions to charity", null, null, null, null, null));
                categoryDao.insert(new Category("Personal Care", CategoryType.EXPENSE, "💅", "Haircuts, toiletries", null, null, null, null, null));
                categoryDao.insert(new Category("Electronics", CategoryType.EXPENSE, "📱", "Gadgets and devices", null, null, null, null, null));
                categoryDao.insert(new Category("Books & Magazines", CategoryType.EXPENSE, "📖", "Reading materials", null, null, null, null, null));
                categoryDao.insert(new Category("Software & Apps", CategoryType.EXPENSE, "💾", "Digital purchases", null, null, null, null, null));
                categoryDao.insert(new Category("Hobbies", CategoryType.EXPENSE, "🎨", "Supplies for hobbies", null, null, null, null, null));
                categoryDao.insert(new Category("Home Decor", CategoryType.EXPENSE, "🖼️", "Furniture, decorations", null, null, null, null, null));
                categoryDao.insert(new Category("Taxes", CategoryType.EXPENSE, "🧾", "Income and property taxes", null, null, null, null, null));

                // NEW Investment Categories (IDs starting from 46 assumed)
                categoryDao.insert(new Category("ETFs", CategoryType.TRIP, "🧺", "Exchange Traded Funds", null, null, null, null, null));
                categoryDao.insert(new Category("Gold/Silver", CategoryType.TRIP, "🥇", "Precious metals", null, null, null, null, null));
                categoryDao.insert(new Category("Peer-to-Peer Lending", CategoryType.TRIP, "🤝", "P2P loans", null, null, null, null, null));
                categoryDao.insert(new Category("Art & Collectibles", CategoryType.TRIP, "🖼️", "Valuable items", null, null, null, null, null));
                categoryDao.insert(new Category("Savings Bonds", CategoryType.TRIP, "✉️", "Government savings bonds", null, null, null, null, null));
                categoryDao.insert(new Category("Venture Capital", CategoryType.TRIP, "🚀", "Startup investments", null, null, null, null, null));
                categoryDao.insert(new Category("Forex Trading", CategoryType.TRIP, "💱", "Foreign exchange markets", null, null, null, null, null));
                categoryDao.insert(new Category("REITs", CategoryType.TRIP, "🏢", "Real Estate Investment Trusts", null, null, null, null, null));
                categoryDao.insert(new Category("Annuities", CategoryType.TRIP, "⏳", "Retirement income products", null, null, null, null, null));
                categoryDao.insert(new Category("Private Equity", CategoryType.TRIP, "💼", "Investment in private companies", null, null, null, null, null));
//
//
//                // Original Accounts (IDs: 1-3 assumed, 15 params for constructor)
                accountDao.insert(new Account("Main Bank Account", AccountType.BANK, 0.00, 0.00, 0.0, null, null, "USD", null, null, null, null, null, null, null));
//                accountDao.insert(new Account("Credit Card Gold", AccountType.CREDIT_CARD, -500.00, -500.00, 0.0, null, null, "USD", null, null, null, null, null, null, null));
//                accountDao.insert(new Account("Cash Wallet", AccountType.WALLET, 200.00, 200.00, 0.0, null, null, "USD", null, null, null, null, null, null, null));
//
//                // NEW Accounts (IDs starting from 4 assumed)
//                accountDao.insert(new Account("Savings Plus",AccountType.BANK, 15000.00, 15000.00, 0.0, null, null, "USD", null, null, null, null, null, null, null));
//                accountDao.insert(new Account("Travel Rewards Card",AccountType.CREDIT_CARD, -100.00, -100.00, 0.0, null, null, "USD", null, null, null, null, null, null, null));
//                accountDao.insert(new Account("Brokerage Account", AccountType.INVESTMENT, 7500.00, 7500.00, 0.0, null, null, "USD", null, null, null, null, null, null, null));
//                accountDao.insert(new Account("Emergency Fund", AccountType.BANK, 10000.00, 10000.00, 0.0, null, null, "USD", null, null, null, null, null, null, null));
//                accountDao.insert(new Account("PayPal Balance", AccountType.E_WALLET, 350.00, 350.00, 0.0, null, null, "USD", null, null, null, null, null, null, null));
//                accountDao.insert(new Account("Business Checking", AccountType.BANK, 12000.00, 12000.00, 0.0, null, null, "USD", null, null, null, null, null, null, null));
//                accountDao.insert(new Account("Student Loan Account", AccountType.LOAN, -25000.00, -25000.00, 0.0, null, null, "USD", null, null, null, null, null, null, null));
//                accountDao.insert(new Account("Crypto Wallet", AccountType.E_WALLET, 1200.00, 1200.00, 0.0, null, null, "USD", null, null, null, null, null, null, null)); // Account ID 11
//                accountDao.insert(new Account("Vacation Savings", AccountType.BANK, 800.00, 800.00, 0.0, null, null, "USD", null, null, null, null, null, null, null)); // Account ID 12
//                accountDao.insert(new Account("Store Credit Card",AccountType.CREDIT_CARD, -50.00, -50.00, 0.0, null, null, "USD", null, null, null, null, null, null, null)); // Account ID 13
//
//
//                long dayMillis = TimeUnit.DAYS.toMillis(1);
//                long monthMillis = TimeUnit.DAYS.toMillis(30); // Approx for a month
//
//                // Original Transactions (14 params for constructor, using fully qualified enums)
//                // Assuming Category IDs 1 (Salary), 6 (Groceries), 16 (Stocks) and Account IDs 1 (Main Bank Account), 2 (Credit Card Gold), 3 (Cash Wallet)
//                transactionDao.insert(new Transaction(1L, 1L, "Monthly Salary", TransactionType.CREDIT, 2500.00, new Date(System.currentTimeMillis() - 5*dayMillis), null, null, null, "Company XYZ",null,null,null,null));
//                transactionDao.insert(new Transaction(2L, 6L, "Weekly Groceries", TransactionType.DEBIT, -75.50, new Date(System.currentTimeMillis() - 4*dayMillis), null, null, null, "Supermarket",null,null,null,null));
//                transactionDao.insert(new Transaction(1L, 16L, "Stock Purchase", TransactionType.DEBIT, -1000.00, new Date(System.currentTimeMillis() - 3*dayMillis), null, null, null, "Brokerage App",null,null,null,null));
//                transactionDao.insert(new Transaction(1L, 7L, "Monthly Rent", TransactionType.DEBIT, -1200.00, new Date(System.currentTimeMillis() - 2*dayMillis), null, null, null, "Landlord",null,null,null,null));
//                transactionDao.insert(new Transaction(3L, 10L, "Dinner Out", TransactionType.DEBIT, -50.00, new Date(System.currentTimeMillis() - 1*dayMillis), null, null, null, "Restaurant ABC",null,null,null,null));
//
//                // NEW Transactions (using various new categories and accounts)
//                // Account IDs: 1-13, Category IDs: 1-55
//                transactionDao.insert(new Transaction(4L, 21L, "Consulting Payment", TransactionType.CREDIT, 1200.00, new Date(System.currentTimeMillis() - 10*dayMillis), null, null, null, "Client Corp", null, null, null, null)); // Savings Plus, Consulting Gig
//                transactionDao.insert(new Transaction(5L, 31L, "Dog Food", TransactionType.DEBIT, -45.00, new Date(System.currentTimeMillis() - 9*dayMillis), null, null, null, "Pet Store", null, null, null, null)); // Travel Rewards Card, Pet Care
//                transactionDao.insert(new Transaction(6L, 46L, "ETF Purchase", TransactionType.DEBIT, -500.00, new Date(System.currentTimeMillis() - 8*dayMillis), null, null, null, "InvestPro Platform", null, null, null, null)); // Brokerage Account, ETFs
//                transactionDao.insert(new Transaction(1L, 22L, "Etsy Sale", TransactionType.CREDIT, 85.00, new Date(System.currentTimeMillis() - 7*dayMillis), null, null, null, "Online Customer", null, null, null, null)); // Main Bank Account, Online Sales
//                transactionDao.insert(new Transaction(2L, 32L, "Plumbing Repair", TransactionType.DEBIT, -120.00, new Date(System.currentTimeMillis() - 6*dayMillis), null, null, null, "Handyman Services", null, null, null, null)); // Credit Card Gold, Home Maintenance
//                transactionDao.insert(new Transaction(7L, 11L, "Concert Tickets", TransactionType.DEBIT, -250.00, new Date(System.currentTimeMillis() - 5*dayMillis), null, null, null, "TicketMaster", null, null, null, null)); // Emergency Fund, Entertainment
//                transactionDao.insert(new Transaction(8L, 23L, "Birthday Gift", TransactionType.CREDIT, 100.00, new Date(System.currentTimeMillis() - 4*dayMillis), null, null, null, "Family", null, null, null, null)); // PayPal Balance, Gifts Received
//                transactionDao.insert(new Transaction(9L, 33L, "Flight Tickets", TransactionType.DEBIT, -700.00, new Date(System.currentTimeMillis() - 3*dayMillis), null, null, null, "Airline X", null, null, null, null)); // Business Checking, Travel & Vacation
//                transactionDao.insert(new Transaction(11L, 19L, "Bitcoin Purchase", TransactionType.DEBIT, -200.00, new Date(System.currentTimeMillis() - 2*dayMillis), null, null, null, "Crypto Exchange", null, null, null, null)); // Crypto Wallet, Cryptocurrency
//                transactionDao.insert(new Transaction(3L, 40L, "New Phone Case", TransactionType.DEBIT, -30.00, new Date(System.currentTimeMillis() - 1*dayMillis), null, null, null, "Accessory Shop", null, null, null, null)); // Cash Wallet, Electronics
//                transactionDao.insert(new Transaction(1L, 15L, "Music Subscription", TransactionType.DEBIT, -15.00, new Date(System.currentTimeMillis() - 12*dayMillis), null, null, null, "Spotify", null, null, null, null)); // Main Bank, Subscriptions
//                transactionDao.insert(new Transaction(4L, 25L, "Book Royalties", TransactionType.CREDIT, 75.00, new Date(System.currentTimeMillis() - 11*dayMillis), null, null, null, "Publisher", null, null, null, null)); // Savings Plus, Royalties
//                transactionDao.insert(new Transaction(6L, 47L, "Gold Coin", TransactionType.DEBIT, -300.00, new Date(System.currentTimeMillis() - 10*dayMillis), null, null, null, "Mint", null, null, null, null)); // Brokerage Account, Gold/Silver
//                transactionDao.insert(new Transaction(2L, 35L, "Car Insurance Premium", TransactionType.DEBIT, -60.00, new Date(System.currentTimeMillis() - 9*dayMillis), null, null, null, "InsureCo", null, null, null, null)); // Credit Card Gold, Insurance
//                transactionDao.insert(new Transaction(7L, 37L, "Gym Membership", TransactionType.DEBIT, -90.00, new Date(System.currentTimeMillis() - 8*dayMillis), null, null, null, "FitClub", null, null, null, null)); // Emergency Fund, Fitness & Wellness
//                transactionDao.insert(new Transaction(12L, 13L, "University Textbooks", TransactionType.DEBIT, -200.00, new Date(System.currentTimeMillis() - 1*dayMillis), null, null, null, "Campus Store", null, null, null, null)); // Vacation Savings, Education
//                transactionDao.insert(new Transaction(5L, 29L, "Stock Options Vesting", TransactionType.CREDIT, 500.00, new Date(System.currentTimeMillis() - 14*dayMillis), null, null, null, "Company Stock Plan", null, null, null, null)); // Travel Rewards Card, Stock Options
//                transactionDao.insert(new Transaction(13L, 44L, "New Curtains", TransactionType.DEBIT, -40.00, new Date(System.currentTimeMillis() - 13*dayMillis), null, null, null, "Home Goods", null, null, null, null)); // Store Credit Card, Home Decor
//                transactionDao.insert(new Transaction(8L, 48L, "Lending Club Note", TransactionType.DEBIT, -150.00, new Date(System.currentTimeMillis() - 12*dayMillis), null, null, null, "P2P Platform", null, null, null, null)); // PayPal Balance, Peer-to-Peer Lending
//                transactionDao.insert(new Transaction(10L, 8L, "Water & Electricity Bill", TransactionType.DEBIT, -180.00, new Date(System.currentTimeMillis() - 11*dayMillis), null, null, null, "City Utilities", null, null, null, null)); // Student Loan Account, Utilities
//
//                // NEW Recurring Data
//                // account_id, cata_id, lastTransactionId, recurring_amount, recurring_frequency, recurring_sdt, recurring_edt, recurring_nxt_duedt, recurring_alarm_rem, recurring_is_auto, v1, v2, v3, v4, v5
//                recurringDao.insert(new Recurring(1L, 7L, null, 1200.00, RecurringFrequency.MONTHLY, new Date(123, 2, 1), new Date(125, 0, 15), new Date(125, 0, 10), true, true, "Rent Payment", null, null, null, null));
//                recurringDao.insert(new Recurring(1L, 1L, null, 2500.00, RecurringFrequency.MONTHLY, new Date(123, 2, 5), new Date(125, 0, 25), new Date(125, 0, 20), true, true, "Salary Deposit", null, null, null, null));
//                recurringDao.insert(new Recurring(2L, 15L, null, 15.00, RecurringFrequency.MONTHLY, new Date(123, 2, 10), new Date(125, 1, 10), new Date(125, 1, 5), true, false, "Music Subscription", null, null, null, null));
//                recurringDao.insert(new Recurring(1L, 8L, null, 150.00, RecurringFrequency.MONTHLY, new Date(123, 2, 15), new Date(125, 1, 20), new Date(125, 1, 15), true, true, "Utilities Bill", null, null, null, null));
//                recurringDao.insert(new Recurring(4L, 25L, null, 75.00, RecurringFrequency.QUARTERLY, new Date(123, 3, 1), new Date(125, 2, 15), new Date(125, 2, 10), false, true, "Book Royalties", null, null, null, null));
//                recurringDao.insert(new Recurring(6L, 16L, null, 500.00, RecurringFrequency.MONTHLY, new Date(123, 3, 5), new Date(125, 2, 25), new Date(125, 2, 20), true, true, "Monthly Investment", "Stocks", null, null, null));
//                recurringDao.insert(new Recurring(7L, 37L, null, 90.00, RecurringFrequency.MONTHLY, new Date(123, 3, 10), new Date(125, 3, 10), new Date(125, 3, 5), true, false, "Gym Membership", null, null, null, null));
//                recurringDao.insert(new Recurring(9L, null, null, 200.00, RecurringFrequency.MONTHLY, new Date(123, 3, 15), new Date(125, 3, 20), new Date(125, 3, 15), true, true, "Loan Repayment", "Student Loan", null, null, null));
//                recurringDao.insert(new Recurring(1L, 15L, null, 10.00, RecurringFrequency.MONTHLY, new Date(123, 4, 1), new Date(125, 4, 15), new Date(125, 4, 10), false, false, "Cloud Storage", null, null, null, null));
//                recurringDao.insert(new Recurring(5L, 31L, null, 25.00, RecurringFrequency.WEEKLY, new Date(123, 4, 5), new Date(125, 4, 25), new Date(125, 4, 20), false, true, "Pet Food Subscription", null, null, null, null));
//                recurringDao.insert(new Recurring(12L, null, null, 50.00, RecurringFrequency.MONTHLY, new Date(123, 4, 10), new Date(125, 5, 10), new Date(125, 5, 5), true, true, "Vacation Savings Deposit", null, null, null, null));
//                recurringDao.insert(new Recurring(1L, 4L, null, 300.00, RecurringFrequency.QUARTERLY, new Date(123, 4, 15), new Date(125, 5, 20), new Date(125, 5, 15), true, true, "Property Tax Savings", null, null, null, null));
//                recurringDao.insert(new Recurring(8L, 26L, null, 50.00, RecurringFrequency.MONTHLY, new Date(123, 5, 1), new Date(125, 6, 15), new Date(125, 6, 10), false, true, "Cashback Rewards Transfer", null, null, null, null));
//                recurringDao.insert(new Recurring(6L, 17L, null, 100.00, RecurringFrequency.MONTHLY, new Date(123, 5, 5), new Date(125, 6, 25), new Date(125, 6, 20), true, true, "Mutual Fund SIP", null, null, null, null));
//                recurringDao.insert(new Recurring(1L, 38L, null, 20.00, RecurringFrequency.YEARLY, new Date(123, 5, 10), new Date(125, 7, 10), new Date(125, 7, 5), true, false, "Charity Donation", "Annual", null, null, null));
//                recurringDao.insert(new Recurring(11L, 19L, null, 50.00, RecurringFrequency.WEEKLY, new Date(123, 5, 15), new Date(125, 7, 20), new Date(125, 7, 15), false, true, "Crypto DCA", "Bitcoin", null, null, null));
//                recurringDao.insert(new Recurring(4L, 24L, null, 200.00, RecurringFrequency.MONTHLY, new Date(123, 0, 10), new Date(125, 8, 15), new Date(125, 8, 10), true, true, "Interest Income Transfer", null, null, null, null));
//                recurringDao.insert(new Recurring(2L, 42L, null, 30.00, RecurringFrequency.MONTHLY, new Date(123, 0, 20), new Date(125, 9, 25), new Date(125, 9, 20), false, true, "Software Subscription", "IDE", null, null, null));
//                recurringDao.insert(new Recurring(10L, null, null, 150.00, RecurringFrequency.MONTHLY, new Date(123, 1, 5), new Date(125, 10, 10), new Date(125, 10, 1), true, true, "Personal Loan EMI", null, null, null, null));


            });
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
}
