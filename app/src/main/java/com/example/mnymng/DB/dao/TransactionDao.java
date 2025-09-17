package com.example.mnymng.DB.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mnymng.DB.enums.CategoryType; // Added import
import com.example.mnymng.DB.models.Transaction;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Transaction transaction);

    @Update
    void update(Transaction transaction);

    @Delete
    void delete(Transaction transaction);

    @Query("SELECT * FROM transactions WHERE trns_id = :transactionId")
    LiveData<Transaction> getTransactionById(long transactionId);

    @Query("SELECT * FROM transactions WHERE trns_id = :transactionId")
   Transaction getTransactionByIdNoneLive(long transactionId);

    @Query("SELECT * FROM transactions ORDER BY trns_date DESC")
    LiveData<List<Transaction>> getAllTransactions();

    @Query("SELECT * FROM transactions WHERE account_id = :accountId ORDER BY trns_date DESC")
    LiveData<List<Transaction>> getTransactionsByAccountId(long accountId);

    // New synchronous method to get transactions by accountId
    @Query("SELECT * FROM transactions WHERE account_id = :accountId ORDER BY trns_date DESC")
    List<Transaction> getTransactionsByAccountIdSync(long accountId);

    // New method to get transactions by CategoryType
    @Query("SELECT t.* FROM transactions t INNER JOIN categories c ON t.cata_id = c.cata_id WHERE c.cata_type = :categoryType ORDER BY trns_date DESC")
    LiveData<List<Transaction>> getTransactionsByCategoryType(CategoryType categoryType);

//    @Query("SELECT * FROM `transaction` WHERE acc_id = :accountId AND cata_id IN (SELECT id FROM category WHERE category_type = :categoryType) ORDER BY trns_date DESC")
//    LiveData<List<Transaction>> getTransactionsByAccountAndCategory(long accountId, String string);
}
