package com.example.mnymng.DB.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mnymng.DB.enums.CategoryType;
import com.example.mnymng.DB.models.CategorySpending;
import com.example.mnymng.DB.models.Transaction;

import java.util.Date;
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

    @Query("SELECT * FROM transactions WHERE account_id = :accountId ORDER BY trns_date DESC")
    List<Transaction> getTransactionsByAccountIdSync(long accountId);

    @Query("SELECT t.* FROM transactions t INNER JOIN categories c ON t.cata_id = c.cata_id WHERE c.cata_type = :categoryType ORDER BY trns_date DESC")
    LiveData<List<Transaction>> getTransactionsByCategoryType(CategoryType categoryType);

    @Query("SELECT IFNULL(SUM(trns_amount), 0.0) FROM transactions WHERE trns_amount > 0 AND trns_date BETWEEN :startDate AND :endDate")
    LiveData<Double> getIncome(Date startDate, Date endDate);

    @Query("SELECT IFNULL(SUM(trns_amount), 0.0) FROM transactions WHERE trns_amount < 0 AND trns_date BETWEEN :startDate AND :endDate")
    LiveData<Double> getExpenses(Date startDate, Date endDate);

    @Query("SELECT c.cata_name, SUM(t.trns_amount) as total FROM transactions t JOIN categories c ON t.cata_id = c.cata_id WHERE t.trns_amount < 0 AND t.trns_date BETWEEN :startDate AND :endDate GROUP BY c.cata_name")
    LiveData<List<CategorySpending>> getSpendingAnalysis(long startDate, long endDate);
}
