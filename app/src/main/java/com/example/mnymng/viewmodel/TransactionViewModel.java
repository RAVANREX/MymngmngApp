package com.example.mnymng.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mnymng.DB.AppDatabase; // Assuming you have a RoomDatabase class like this
import com.example.mnymng.DB.dao.AccountDao;
import com.example.mnymng.DB.dao.TransactionDao; // Added import
import com.example.mnymng.DB.enums.AccountType;
import com.example.mnymng.DB.enums.CategoryType;
import com.example.mnymng.DB.models.Account;
import com.example.mnymng.DB.models.Transaction; // Added import

import java.util.List;

public class TransactionViewModel extends AndroidViewModel {

    private AccountDao accountDao;
    private TransactionDao transactionDao; // Added TransactionDao field

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        // Initialize your DAOs. Replace AppDatabase.getDatabase(application)
        // with your actual database instance retrieval logic.
        AppDatabase db = AppDatabase.getDatabase(application);
        accountDao = db.accountDao();
        transactionDao = db.transactionDao(); // Initialize TransactionDao
    }

    /**
     * Fetches accounts based on the specified account type.
     * This method directly calls the DAO's getAccountsByType method.
     *
     * @param accountType The type of account to filter by.
     * @return LiveData list of accounts matching the type.
     */
    public LiveData<List<Account>> getAccountsByType(AccountType accountType) {
        return accountDao.getAccountsByType(accountType);
    }

    /**
     * Fetches transactions based on the specified category type.
     * This method directly calls the DAO's getTransactionsByCategoryType method.
     *
     * @param categoryType The type of category to filter by.
     * @return LiveData list of transactions matching the category type.
     */
    public LiveData<List<Transaction>> getTransactionsByCategoryType(CategoryType categoryType) {
        return transactionDao.getTransactionsByCategoryType(categoryType);
    }

    /**
     * Fetches all transactions.
     * This method directly calls the DAO's getAllTransactions method.
     *
     * @return LiveData list of all transactions.
     */
    public LiveData<List<Transaction>> getAllTransactions() {
        return transactionDao.getAllTransactions();
    }

    // You can add other ViewModel methods here as needed for your TransactionHandlerFragment.
    // For example, methods to add a new transaction, fetch transaction history, etc.
}
