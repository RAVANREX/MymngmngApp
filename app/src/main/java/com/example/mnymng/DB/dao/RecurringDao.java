package com.example.mnymng.DB.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mnymng.DB.models.Recurring;

import java.util.List;

@Dao
public interface RecurringDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Recurring recurring);

    @Update
    void update(Recurring recurring);

    @Delete
    void delete(Recurring recurring);
    @Delete
    void deleteAll(List<Recurring> recurring);
    @Query("SELECT * FROM recurring")
    List<Recurring> getAllRecurring();



    @Query("SELECT * FROM recurring WHERE recurring_id = :recurringId")
    LiveData<Recurring> getRecurringById(long recurringId);

    @Query("SELECT * FROM recurring ORDER BY recurring_sdt DESC")
    LiveData<List<Recurring>> getAllRecurringTransactions();

    @Query("SELECT * FROM recurring WHERE recurring_edt IS NOT NULL AND recurring_edt < recurring_nxt_duedt")
    List<Recurring> getCompletedRecurring();
}
