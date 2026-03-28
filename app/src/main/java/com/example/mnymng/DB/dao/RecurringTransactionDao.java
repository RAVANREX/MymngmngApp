package com.example.mnymng.DB.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.mnymng.DB.models.RecurringTransaction;

@Dao
public interface RecurringTransactionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(RecurringTransaction recurringTransaction);
}
