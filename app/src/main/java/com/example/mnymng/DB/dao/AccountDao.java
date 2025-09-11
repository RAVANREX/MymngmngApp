package com.example.mnymng.DB.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mnymng.DB.models.Account;

import java.util.List;

@Dao
public interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Account account);

    @Update
    void update(Account account);

    @Delete
    void delete(Account account);

    @Query("SELECT * FROM accounts WHERE account_id = :accountId")
    LiveData<Account> getAccountById(long accountId);

    @Query("SELECT * FROM accounts ORDER BY account_name ASC")
    LiveData<List<Account>> getAllAccounts();
}
