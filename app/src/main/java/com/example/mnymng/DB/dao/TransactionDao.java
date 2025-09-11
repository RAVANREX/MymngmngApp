package com.example.mnymng.DB.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mnymng.DB.models.Transaction;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Transaction transaction);

    @Update
    void update(Transaction transaction);

    @Delete
    void delete(Transaction transaction);

    @Query("SELECT * FROM transactions WHERE trns_id = :transactionId")
    LiveData<Transaction> getTransactionById(long transactionId);

    @Query("SELECT * FROM transactions ORDER BY trns_date DESC")
    LiveData<List<Transaction>> getAllTransactions();

    @Query("SELECT * FROM transactions WHERE account_id = :accountId ORDER BY trns_date DESC")
    LiveData<List<Transaction>> getTransactionsByAccountId(long accountId);
}
