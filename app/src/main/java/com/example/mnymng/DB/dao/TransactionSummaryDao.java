package com.example.mnymng.DB.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mnymng.DB.models.TransactionSummary;

import java.util.List;

@Dao
public interface TransactionSummaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(TransactionSummary transactionSummary);

    @Update
    void update(TransactionSummary transactionSummary);

    @Delete
    void delete(TransactionSummary transactionSummary);

    @Query("SELECT * FROM transaction_summary WHERE trnsum_id = :summaryId")
    LiveData<TransactionSummary> getTransactionSummaryById(long summaryId);

    @Query("SELECT * FROM transaction_summary ORDER BY trnsum_last_updt DESC")
    LiveData<List<TransactionSummary>> getAllTransactionSummaries();

    @Query("SELECT * FROM transaction_summary WHERE account_id = :accountId ORDER BY trnsum_last_updt DESC")
    LiveData<List<TransactionSummary>> getTransactionSummariesByAccountId(long accountId);
}
