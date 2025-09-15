package com.example.mnymng.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.mnymng.DB.AppDatabase; // Assuming you have a RoomDatabase class like this
import com.example.mnymng.DB.dao.AccountDao;
import com.example.mnymng.DB.enums.AccountType;
import com.example.mnymng.DB.models.Account;

import java.util.List;

public class AccountViewModel extends AndroidViewModel {

    private AccountDao accountDao;

    public AccountViewModel(@NonNull Application application) {
        super(application);
        // Initialize your DAOs. Replace AppDatabase.getDatabase(application)
        // with your actual database instance retrieval logic.
        AppDatabase db = AppDatabase.getDatabase(application);
        accountDao = db.accountDao();
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
     * Fetches all accounts.
     * This method directly calls the DAO's getAllAccounts method.
     *
     * @return LiveData list of all accounts.
     */
    public LiveData<List<Account>> getAllAccounts() {
        return accountDao.getAllAccounts();
    }

    // You can add other ViewModel methods here as needed.
}
