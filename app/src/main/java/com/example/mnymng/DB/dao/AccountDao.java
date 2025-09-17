package com.example.mnymng.DB.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mnymng.DB.enums.AccountType;
import com.example.mnymng.DB.enums.CategoryType;
import com.example.mnymng.DB.models.Account;
import com.example.mnymng.DB.models.Transaction;

import java.util.List;

@Dao
public interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Account account);

    @Update
    void update(Account account);

    @Delete
    void delete(Account account);

    @Query("SELECT * FROM accounts WHERE account_id = :accountId")
    Account getAccountById(long accountId);

    @Query("SELECT * FROM accounts ORDER BY account_name ASC")
    LiveData<List<Account>> getAllAccounts();

    @Query("SELECT * FROM accounts WHERE account_type = :accountType ORDER BY account_name ASC")
    LiveData<List<Account>> getAccountsByType(AccountType accountType); // New method

}
